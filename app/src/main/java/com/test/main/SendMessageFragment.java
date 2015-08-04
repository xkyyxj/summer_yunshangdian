package com.test.main;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.test.albert.myapplication.R;

import bmobObject.Shop;
import bmobObject.ShopNews;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SendMessageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendMessageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendMessageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String input_text = null;

    private TextView input_area = null;
    private ImageButton send_button = null;

    private OnFragmentInteractionListener mListener;
    private MyButtonListener li = null;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendMessageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SendMessageFragment newInstance(String param1, String param2) {
        SendMessageFragment fragment = new SendMessageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SendMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        li = new MyButtonListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_message, container, false);
        input_area = (TextView)view.findViewById(R.id.send_info_area);
        send_button = (ImageButton)view.findViewById(R.id.send_button);
        send_button.setOnClickListener(li);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void sendMessage(String message)
    {
        Shop shop = null;
        shop = BmobUser.getCurrentUser(getActivity(),Shop.class);
        ShopNews news = new ShopNews();
        news.setShop(shop);
        news.setText(message);
        news.save(getActivity(), new SaveListener() {
            @Override
            public void onSuccess() {
                toast(R.string.send_successful);
            }

            @Override
            public void onFailure(int i, String s) {
                toast(R.string.send_failed);
            }
        });
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void toast(int id)
    {
        String str = getActivity().getResources().getString(id);
        Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
    }

    class MyButtonListener implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            input_text = input_area.getText().toString();
            sendMessage(input_text);
        }
    }

}
