package com.pikon.android_quiz.ui.result;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.pikon.android_quiz.Answer;
import com.pikon.android_quiz.Question;
import com.pikon.android_quiz.R;
import com.pikon.android_quiz.databinding.FragmentQuestionBinding;
import com.pikon.android_quiz.databinding.FragmentResultBinding;
import com.pikon.android_quiz.ui.question.QuestionViewModel;

public class ResultFragment extends Fragment {

    private FragmentResultBinding binding;
    private ResultViewModel resultViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState ) {

        resultViewModel = new ViewModelProvider( this ).get( ResultViewModel.class );
        // TODO quiz argument pass
//        resultViewModel.

        binding = FragmentResultBinding.inflate( inflater, container, false );
        View root = binding.getRoot();

        return root;
    }
}
