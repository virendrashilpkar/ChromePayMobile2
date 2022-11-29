package com.chromepaymobile.Fragments;

import static com.chromepaymobile.R.id.land_certify_btn;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chromepaymobile.R;
import com.squareup.picasso.Picasso;

import java.io.DataInput;

import de.hdodenhof.circleimageview.CircleImageView;

public class LandRegistrationFragment extends Fragment {

    RelativeLayout landCertify;
    TextView fullName,phoneNO,address;
    CircleImageView profileImg;
    ImageView fingerPrintCUc;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_land_registration, container, false);

        landCertify = mView.findViewById(R.id.land_certify_btn);
        fullName = mView.findViewById(R.id.fullNmae);
        phoneNO = mView.findViewById(R.id.phone_no_lr);
        address = mView.findViewById(R.id.address_cus);
        profileImg = mView.findViewById(R.id.image_profile_lr);
        fingerPrintCUc = mView.findViewById(R.id.finger_print_c_uc);

        sharedPreferences = getContext().getSharedPreferences("CustomerPreferences", Context.MODE_PRIVATE);
        String photo = sharedPreferences.getString("IDphoto","");
        String name = sharedPreferences.getString("fullname","");
        String number = sharedPreferences.getString("phone","");
        int fingerprint = sharedPreferences.getInt("fingerPrint",0);
        String addresss = sharedPreferences.getString("address","");

        Picasso.get().load(photo).placeholder(R.drawable.profile_01).fit().into(profileImg);
        fullName.setText(name);
        phoneNO.setText(number);
        address.setText(addresss);

        if (fingerprint == 0){
            fingerPrintCUc.setBackground(getActivity().getResources().getDrawable(R.drawable.cross));

        }else {
            fingerPrintCUc.setBackground(getActivity().getResources().getDrawable(R.drawable.home_page_icon_22));

        }


        landCertify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.land_certify_dialog,null);

                ImageView dis = (ImageView)dialogView.findViewById(R.id.land_dis);
                ImageView landImg = (ImageView)dialogView.findViewById(R.id.land_certify_img);

                dialog.setContentView(dialogView);

                sharedPreferences = getContext().getSharedPreferences("CustomerPreferences", Context.MODE_PRIVATE);
                String land = sharedPreferences.getString("landRegistration","");

                Picasso.get().load(land).placeholder(R.drawable.image_2022_11_02t11_17_11_501z).fit().into(landImg);
                dis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return mView;
    }
}