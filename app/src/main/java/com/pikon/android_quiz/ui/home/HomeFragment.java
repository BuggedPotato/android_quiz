package com.pikon.android_quiz.ui.home;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.pikon.android_quiz.MainActivity;
import com.pikon.android_quiz.Quiz;
import com.pikon.android_quiz.R;
import com.pikon.android_quiz.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel homeViewModel;

    public View onCreateView( @NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState ) {
        homeViewModel =
                new ViewModelProvider( this ).get( HomeViewModel.class );

        homeViewModel.getQuiz().observe(getViewLifecycleOwner(), new Observer<Quiz>() {
            @Override
            public void onChanged( Quiz quiz ) {
                MainActivity.loadedQuiz = quiz;
                showFileData( quiz );
            }
        });

        binding = FragmentHomeBinding.inflate( inflater, container, false );
        View root = binding.getRoot();

        final Button btnLoadFile = binding.btnLoadFile;
        btnLoadFile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                mGetContent.launch( "text/plain" );
            }
        } );

        binding.btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString( "quizUri", homeViewModel.getQuiz().getValue().getUri().getPath() );
                Navigation.findNavController( root ).navigate( R.id.action_nav_home_to_nav_quiz, bundle,
                        new NavOptions.Builder()
                                .setEnterAnim( androidx.navigation.ui.R.animator.nav_default_enter_anim )
                                .setExitAnim( androidx.navigation.ui.R.animator.nav_default_exit_anim )
                                .setPopExitAnim( androidx.navigation.ui.R.animator.nav_default_exit_anim )
                                .build()
                );
            }
        });

        return root;
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult( new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult( Uri uri ) {
                    homeViewModel.setQuiz( getContext(), uri );
                }
            } );

    private void showFileData( @Nullable Quiz quiz ) {
        if( quiz == null ){
            Log.w( "DEBUG", "Unable to parse text file" );
            binding.tvQuestionsCount.setText( "Invalid file data" );
            binding.btnStartQuiz.setEnabled( false );
            return;
        }
        Log.d( "DEBUG", quiz.toString() );
        binding.tvQuestionsCount.setText( String.format( "%d questions found", quiz.getQuestions().size() ) );
        binding.btnStartQuiz.setEnabled( true );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}