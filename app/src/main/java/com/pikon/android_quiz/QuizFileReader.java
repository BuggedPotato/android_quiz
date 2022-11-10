package com.pikon.android_quiz;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuizFileReader {
    public static Quiz getFileData( Context context, Uri uri ) {
        try {
            File f = QuizFileReader.copyFile( context, uri );
            Quiz quiz = new Quiz();
            Scanner scanner = new Scanner( f );
            scanner.useDelimiter( Pattern.compile( "\\R{2,}" ) );
            while ( scanner.hasNext() ) {
                ArrayList<String> data = new ArrayList<>( Arrays.asList( scanner.next().split( "\\n" ) ) );
                Log.d( "DEBUG", Arrays.toString( data.toArray() ) );
                String title = data.get( 0 );
                data.remove( 0 );
                ArrayList<Answer> answers = new ArrayList<>();
                for ( String line : data ) {
                    Matcher m = Pattern.compile( "\\s+[\\w\\s]+" ).matcher( line );
                    boolean correct = Pattern.compile( "^>>>[A-Z]\\)" ).matcher( line ).matches();
                    if ( !m.find() )
                        return null;
                    Answer answer = new Answer( m.group( 0 ).trim(), correct );
                    answers.add( answer );
                }
                Question question = new Question( title, answers );
                quiz.addQuestion( question );
            }
            return quiz;
        } catch ( IOException e ) {
            Log.e( "DEBUG", String.format( "File %s not found", uri.getPath() ) );
            e.printStackTrace();
        }
        return null;
    }

    public static File copyFile( Context context, Uri uri ) throws IOException {
        InputStream inputStream = context.getContentResolver().openInputStream( uri );
        File targetFile = new File( Environment.getExternalStorageDirectory(), "targetFile.tmp" );
        Files.copy( inputStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING );
        inputStream.close();
        return targetFile;
    }
}
