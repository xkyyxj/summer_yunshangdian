package com.example.administrator.myapplication.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.http.HttpHelper;
import com.example.administrator.myapplication.manager.ThreadManager;
import com.example.administrator.myapplication.utils.DrawableUtils;
import com.example.administrator.myapplication.utils.FileUtils;
import com.example.administrator.myapplication.utils.IOUtils;
import com.example.administrator.myapplication.utils.LogUtils;
import com.example.administrator.myapplication.utils.StringUtils;
import com.example.administrator.myapplication.utils.SystemUtils;
import com.example.administrator.myapplication.utils.UIUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by Administrator on 2015/7/7.
 */
public class ImageLoader {
    /** ͼƬ���ص��̳߳����� */
    public static final String THREAD_POOL_NAME = "IMAGE_THREAD_POOL";
    /** ͼƬ����������� */
    public static final int MAX_DRAWABLE_COUNT = 100;
    /** ͼƬ��KEY���� */
    private static ConcurrentLinkedQueue<String> mKeyCache =  new ConcurrentLinkedQueue<String>();
    /** ͼƬ�Ļ��� */
    private static Map<String, Drawable> mDrawableCache = new ConcurrentHashMap<String, Drawable>();
    private static BitmapFactory.Options mOptions = new BitmapFactory.Options();
    /** ͼƬ���ص��̳߳� */
    private static ThreadManager.ThreadPoolProxy mThreadPool = ThreadManager.getSinglePool(THREAD_POOL_NAME);
    /** ���ڼ�¼ͼƬ���ص������Ա�ȡ�� */
    private static ConcurrentHashMap<String, Runnable> mMapRuunable = new ConcurrentHashMap<String, Runnable>();
    /** ͼƬ���ܴ�С */
    private static long mTotalSize;

    static {
        mOptions.inDither = false;// ����Ϊfalse����������ͼƬ�Ķ���ֵ��������ͼƬ���ڴ�ռ��
        mOptions.inPurgeable = true;// ����Ϊture����ʾ����ϵͳ���ڴ治��ʱ��ɾ��bitmap�����顣
        mOptions.inInputShareable = true;// ��inPurgeable���ʹ�ã����inPurgeable��false����ô�ò����������ԣ���ʾ�Ƿ��bitmap��������й���
    }

    /** ����ͼƬ */
    public static void load(ImageView view, String url) {
        if (view == null || StringUtils.isEmpty(url)) {
            return;
        }
        view.setTag(url);//�ѿؼ���ͼƬ��url���а󶨣���Ϊ������һ����ʱ�ģ��ȼ����������Ҫ�ж��ÿؼ��Ƿ�͸�urlƥ��
        Drawable drawable = loadFromMemory(url);//���ڴ��м���
        if (drawable != null) {
            setImageSafe(view, url, drawable);//����ڴ��м��ص��ˣ�ֱ������ͼƬ
        } else {
            view.setImageResource(R.drawable.ic_default);//���û���ص�������Ĭ��ͼƬ�����첽����
            asyncLoad(view, url);
        }
    }

    /** �첽���� */
    private static void asyncLoad(final ImageView view, final String url) {
        //�ȴ���һ��runnable��װִ�й���
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mMapRuunable.remove(url);
                Drawable drawable = loadFromLocal(url);
                if (drawable == null) {
                    drawable = loadFromNet(url);
                }
                if (drawable != null) {
                    setImageSafe(view, url, drawable);
                }
            }
        };
        cancel(url);//��ȡ�����url������
        mMapRuunable.put(url, runnable);//��ס���runnable���Ա����ȡ��
        mThreadPool.execute(runnable);//ִ������
    }

    /** ȡ������ */
    public static void cancel(String url) {
        Runnable runnable = mMapRuunable.remove(url);//����url����ȡָ����runnable
        if (runnable != null) {
            mThreadPool.cancel(runnable);//���̳߳���ɾ����������������Ѿ���ʼ�����ˣ����޷�ɾ��
        }
    }

    /** ���ڴ��м��� */
    private static Drawable loadFromMemory(String url) {
        Drawable drawable = mDrawableCache.get(url);
        if (drawable != null) {//���ڴ��л�ȡ���ˣ���Ҫ���·ŵ��ڴ���е�����Ա�����LRC
            //һ�㻺���㷨�����֣���һ��LFU����ʹ�ô������ж�ɾ�����ȼ���ʹ�ô������ٵ�����ɾ��
            //����һ������LRC�����ǰ����ʹ��ʱ�����ж�ɾ�����ȼ������ʹ��ʱ��Խ�������ɾ��
            addDrawableToMemory(url, drawable);
        }
        return drawable;
    }

    /** �ӱ����豸�м��� */
    private static Drawable loadFromLocal(String url) {
        Bitmap bitmap = null;
        Drawable drawable = null;
        String path = FileUtils.getIconDir();
        FileInputStream fis = null;
        try {
            //��ȡ��
            fis = new FileInputStream(new File(path + url));
            if (fis != null) {
                // BitmapFactory.decodeByteArray(data, offset, length)
                // BitmapFactory.decodeFile(pathName)
                // BitmapFactory.decodeStream(is)
                // ������������Դ���֪�����Ƕ�����Java�㴴��byte���飬Ȼ������ݴ��ݸ����ش��롣
                // ��������ǰ��ļ����������ݸ����ش��룬�ɱ��ش���ȥ����ͼƬ
                // �ŵ㣬�����Ǳ��ش��봴���ģ���ôbyte������ڴ�ռ�ò����㵽Ӧ���ڴ��У�����һ���ڴ治�㣬�����bitmap��������յ�����bitmap���ᱻ����
                // ����ʾ��ʱ�򣬷���bitmap������Ϊ��ʱ�������ٴθ����ļ�������ȥ����ͼƬ����ʱ�������ڼ��غ�ʱ��ɽ��濨�٣����ܱ�OOMҪ�õöࡣ
                // ���ڱ��ش����ڴ���ͼƬʱ��û�ж�ͼƬ����У�飬��������ļ������������߸����Ͳ���һ��ͼƬʱ��ϵͳҲ���ᱨ����Ȼ�᷵��һ��bitmap,�������bitmap��һ������ɫ��bitmap��
                // ��������������ͼƬ��ʱ��һ��Ҫ����һ����ʱ�ļ����أ�����������ˣ��ٶ�ͼƬ������������
                bitmap = BitmapFactory.decodeFileDescriptor(fis.getFD(), null, mOptions);
            }
            if (null != bitmap) {//��bitmapת����drawable
                drawable = new BitmapDrawable(UIUtils.getResources(), bitmap);
            }
            if (drawable != null) {//�ŵ��ڴ滺�������
                addDrawableToMemory(url, drawable);
            }
        } catch (OutOfMemoryError e) {
            mKeyCache.clear();
            mDrawableCache.clear();
            LogUtils.e(e);
        } catch (Exception e) {
            LogUtils.e(e);
        } finally {
            IOUtils.close(fis);
        }
        return drawable;
    }

    /** ���������ͼƬ */
    private static Drawable loadFromNet(String url) {
        HttpHelper.HttpResult httpResult = HttpHelper.download(HttpHelper.URL + "image?name=" + url);
        InputStream stream = null;
        if (httpResult == null || (stream = httpResult.getInputStream()) == null) {//��������
            return null;
        }
        String tempPath = FileUtils.getIconDir() + url + ".temp";
        String path = FileUtils.getIconDir() + url;
        FileUtils.writeFile(stream, tempPath, true);//���������ر����ڱ���
        if (httpResult != null) {//�ر���������
            httpResult.close();
        }
        FileUtils.copy(tempPath, path, true);//���и���
        return loadFromLocal(url);//�ӱ��ؼ���
    }

    /** ��ӵ��ڴ� */
    private static void addDrawableToMemory(String url, Drawable drawable) {
        mKeyCache.remove(url);
        mDrawableCache.remove(url);
        //������ڵ���100�ţ�����ͼƬ���ܴ�С����Ӧ�����ڴ���ķ�֮һ��ɾ��ǰ���
        while (mKeyCache.size() >= MAX_DRAWABLE_COUNT || mTotalSize >= SystemUtils.getOneAppMaxMemory() / 4) {
            String firstUrl = mKeyCache.remove();
            Drawable remove = mDrawableCache.remove(firstUrl);
            mTotalSize -= DrawableUtils.getDrawableSize(remove);
        }
        mKeyCache.add(url);//���
        mDrawableCache.put(url, drawable);
        mTotalSize += DrawableUtils.getDrawableSize(drawable);
    }

    /** ���ø��ؼ�ͼƬ */
    private static void setImageSafe(final ImageView view, final String url, final Drawable drawable) {
        if (drawable == null && view.getTag() == null) {
            return;
        }
        UIUtils.runInMainThread(new Runnable() {//��Ҫ�����߳�������
            @Override
            public void run() {
                Object tag;//�����߳����жϣ���������ͬ��
                if ((tag = view.getTag()) != null) {
                    String str = (String) tag;
                    if (StringUtils.isEquals(str, url)) {//������url�Ϳؼ�ƥ��
                        view.setImageDrawable(drawable);//�ͽ���ͼƬ����
                    }
                }
            }
        });
    }
}
