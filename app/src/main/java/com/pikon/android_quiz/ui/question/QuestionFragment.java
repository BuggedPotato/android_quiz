package com.pikon.android_quiz.ui.question;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.pikon.android_quiz.Answer;
import com.pikon.android_quiz.MainActivity;
import com.pikon.android_quiz.Question;
import com.pikon.android_quiz.R;
import com.pikon.android_quiz.databinding.FragmentQuestionBinding;
import com.pikon.android_quiz.ui.home.DrawerLocker;
import com.pikon.android_quiz.ui.home.HomeViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class QuestionFragment extends Fragment {

    private FragmentQuestionBinding binding;
    private QuestionViewModel questionViewModel;

    public View onCreateView( @NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState ) {
        Fragment selfRef = this;
        questionViewModel = new ViewModelProvider( this ).get( QuestionViewModel.class );
        questionViewModel.getQuestion().observe(getViewLifecycleOwner(), new Observer<Question>() {
            @Override
            public void onChanged(Question question) {
                Log.d( "DEBUG", String.valueOf( questionViewModel.getQuiz().getValue() == null ) );
                if( question == null ) {
                    Log.d( "DEBUG", "=== END ===" );
                    Bundle bundle = new Bundle();
                    bundle.putSerializable( "quiz", questionViewModel.getQuiz().getValue() );
                    NavHostFragment.findNavController( selfRef )
                            .navigate( R.id.action_nav_quiz_to_nav_result, bundle );
                    return;
                }
                showQuestionData( question );
                questionViewModel.clearCheckedAnswers();
            }
        });
        questionViewModel.setQuiz(  getContext(), Uri.parse( getArguments().getString( "quizUri" ) ) );
        questionViewModel.setQuestion( questionViewModel.getQuiz().getValue() );

        binding = FragmentQuestionBinding.inflate( inflater, container, false );
        View root = binding.getRoot();

        binding.btnNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionViewModel.getQuestion().getValue().answer( questionViewModel.getCheckedAnswers().getValue().toArray(new Answer[0]) );
                Log.d( "DEBUG", questionViewModel.getQuestion().getValue().toString() );
                questionViewModel.setQuestion( questionViewModel.getQuiz().getValue() );
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder
                        .setTitle( "Are you sure?" )
                        .setMessage( "Are you sure you want to quit your quiz?" )
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Navigation.findNavController(root).navigate( R.id.action_nav_quiz_to_nav_home );
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
                        })
                        .create();
                builder.show();
            }
        });

        return root;
    }


    private void showQuestionData( Question question ) {
        final TextView tvTitle = binding.tvTitle;
        final LinearLayout llAnswers = binding.llAnswers;
        final String questionNumber = String.format( Locale.ROOT, "%d/%d", questionViewModel.getQuiz().getValue().getCurrentQuestion() + 1, questionViewModel.getQuiz().getValue().getQuestions().size() );
        binding.tvQuestionNumber.setText( questionNumber );
        if( llAnswers.getChildCount() > 0 )
            llAnswers.removeAllViews();
        tvTitle.setText(question.getText());
        for (Answer a : question.getAnswers()) {
            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setText(a.getText());
            checkBox.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    questionViewModel.onCheckAnswer(a, b);
                }
            });
            llAnswers.addView(checkBox);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        ((DrawerLocker)getActivity()).setDrawerEnabled(false);
        Log.d( "DEBUG", "LOCKED" );
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        ((DrawerLocker)getActivity()).setDrawerEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}