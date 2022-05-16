package ru.altqi.physx;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class UncompletedFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // менеджер компоновки, который позволяет получать доступ к layout с наших ресурсов
        //
//        // теперь можем получить наши элементы, расположенные во фрагменте
//        Button button = (Button) view.findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Message from fragment", Toast.LENGTH_LONG).show();
//            }
//        });
        return inflater.inflate(R.layout.fragment_uncompleted, container, false);
    }

}