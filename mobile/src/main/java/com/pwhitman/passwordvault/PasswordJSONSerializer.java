package com.pwhitman.passwordvault;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Philip on 5/7/2015.
 */
public class PasswordJSONSerializer  {
    private Context mContext;
    private String mFilename;

    public PasswordJSONSerializer(Context c, String f){
        mContext = c;
        mFilename = f;
    }

    public ArrayList<Password> loadPasswords() throws IOException, JSONException{
        ArrayList<Password> passwords = new ArrayList<Password>();

        BufferedReader reader = null;
        try{
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null){
                jsonString.append(line);
            }

            JSONArray array = (JSONArray)new JSONTokener(jsonString.toString()).nextValue();

            for(int i = 0; i < array.length(); i++){
                passwords.add(new Password(array.getJSONObject(i)));
            }

        }catch(FileNotFoundException e){
            //Ignoring this
        }finally{
            if(reader != null){
                reader.close();

            }
        }

        return passwords;
    }

    public void savePasswords(ArrayList<Password> passwords) throws JSONException, IOException{
        JSONArray array = new JSONArray();
        for(Password p : passwords){
            array.put(p.toJSON());
        }

        Writer writer = null;
        try{
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }finally{
            if(writer != null){
                writer.close();

            }
        }
    }

}
