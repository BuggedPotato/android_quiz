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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.pikon.android_quiz.Quiz;
import com.pikon.android_quiz.QuizFileReader;
import com.pikon.android_quiz.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    ActivityResultLauncher<String> mGetContent = registerForActivityResult( new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult( Uri uri ) {
                    showFileData( uri );
                }
            } );


    public View onCreateView( @NonNull LayoutInflater inflater,
                              ViewGroup container, Bundle savedInstanceState ) {
        HomeViewModel homeViewModel =
                new ViewModelProvider( this ).get( HomeViewModel.class );

        binding = FragmentHomeBinding.inflate( inflater, container, false );
        View root = binding.getRoot();

//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle( "Totally Not Drill 2" );

//        final TextView textView = binding.textHome;
//        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final Button btnLoadFile = binding.btnLoadFile;
        btnLoadFile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                mGetContent.launch( "text/plain" );
            }
        } );

        return root;
    }

    private void showFileData( Uri uri ) {
        Quiz q = QuizFileReader.getFileData( getContext(), uri );
        if ( q == null ) {
            Log.w( "DEBUG", "Couldn't parse file data" );
            binding.tvQuestionsCount.setText( "Unable to parse data" );
            return;
        }
        Log.d( "DEBUG", q.toString() );
        binding.tvQuestionsCount.setText( String.format( "%d questions found", q.getQuestions().size() ) );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}