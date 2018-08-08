package ai.neuronet.com.palavasmartcity.Fragments.faceLogin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.CameraView;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import ai.neuronet.com.palavasmartcity.Fragments.LoginUsingEmailOrOtp;
import ai.neuronet.com.palavasmartcity.PojoClasses.faceRecognition.FaceRecognition;
import ai.neuronet.com.palavasmartcity.R;
import id.zelory.compressor.Compressor;

/**
 * Created by ${Shailendra} on 07-08-2018.
 */
public class FaceLoginFragment extends Fragment {
    private int captureCount = 0;
    private CameraView cameraView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.facelogin_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }

    private void initView(View view) {
        cameraView = view.findViewById(R.id.camera);
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onCameraOpened(CameraOptions options) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cameraView.capturePicture();
                    }
                }, 2000);
                super.onCameraOpened(options);
            }

            @Override
            public void onCameraClosed() {
                super.onCameraClosed();
            }

            @Override
            public void onPictureTaken(final byte[] jpeg) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
                        captureCount++;
                        if (captureCount < 5) {
                            cameraView.capturePicture();
                        }
                        SaveImageToMemory imageToMemory = new SaveImageToMemory(jpeg, "image" + captureCount);
                        imageToMemory.execute();
//                    }
//                }, 500);
                super.onPictureTaken(jpeg);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class SaveImageToMemory extends AsyncTask<Void, Void, Void> {
        byte[] data;
        String imageName;

        SaveImageToMemory(byte[] data, String imageName) {
            this.data = data;
            this.imageName = imageName;
        }


        @Override
        protected Void doInBackground(Void... params) {
            compressImage(data, imageName);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (imageName.contains("5")) {
                cameraView.stop();
                cameraView.start();
                UploadImages uploadImages = new UploadImages();
                uploadImages.execute();
            }

        }


    }

    private void compressImage(byte[] data, String imageName) {
        File file = getOutputMediaFile("FaceImage", imageName);/* = new File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                            "frontPicture.jpg");*/
        Log.d("FileName : ", file.getAbsolutePath());
        OutputStream os = null;
        try {

            os = new FileOutputStream(file);
            os.write(data);
            os.close();
            Log.e("Saving : ", "doInBackground: " + file.length() / 1024);
//            EmbadImages embadImages = new EmbadImages(imageName);
//            embadImages.execute();
        } catch (IOException e) {
            Log.w("Saving failed : ", "Cannot write to " + file, e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    // Ignore
                }
            }

        }
    }

    public File getOutputMediaFile(String folderName, String imageName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir;


        mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/SmartCity/Neuronet/" + folderName);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        File mediaFile;
        String mImageName = "IMG_" + imageName + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private String imageToBase64(String file) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public class UploadImages extends AsyncTask<Void, Void, String> {

        private JSONObject jsonObject;
        private String result;
        private BufferedInputStream in;
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setMessage("Recognising face...");
        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                Log.d("Shallu", "encodedImage = " + imageToBase64(Environment.getExternalStorageDirectory() + "/SmartCity/Neuronet/FaceImage/IMG_image5.jpg"));
                jsonObject = new JSONObject();
                jsonObject.put("image", imageToBase64(Environment.getExternalStorageDirectory() + "/SmartCity/Neuronet/FaceImage/IMG_image5.jpg"));
//                jsonObject.put("image", "image");
                String data = jsonObject.toString();
                String yourURL = "http://54.203.112.223:8000/face/compare/";
                URL url = new URL(yourURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setFixedLengthStreamingMode(data.getBytes().length);
                connection.setRequestProperty("Content-Type", "application/json");
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                writer.write(data);
                Log.d("Shallu", "Data to php = " + data);
                writer.flush();
                writer.close();
                out.close();
                connection.connect();

                in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        in));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                result = sb.toString();
                Log.d("Shallu", "Response from php = " + result);
                //Response = new JSONObject(result);
                connection.disconnect();
            } catch (Exception e) {
                Log.d("Shallu", "Error Encountered");
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s != null) {
                Log.e("Result ", s);
                Gson gson = new GsonBuilder().create();
                FaceRecognition faceRecognition = gson.fromJson(s, FaceRecognition.class);
                faceRecognition.toString();
                /*Code next lavel*/
            } else {
                loginViaOTP();
                Log.e("Face login failed : ", "Could not recognised face");
            }
        }
    }

    private void loginViaOTP() {
        if (getActivity() == null) {
            return;
        }
        if (!getActivity().isFinishing()) {
            LoginUsingEmailOrOtp loginFragment = new LoginUsingEmailOrOtp();

            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                if (getActivity() == null || getActivity().isFinishing()) {
                    return;
                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("ID", (String) cameraView.getTag());
                loginFragment.setArguments(bundle);
                transaction.replace(R.id.contentPanel, loginFragment, loginFragment.getClass().getCanonicalName()).addToBackStack(loginFragment.getClass().getCanonicalName()).commit();
            }
        }
    }

    public class EmbadImages extends AsyncTask<Void, Void, String> {

        private JSONObject jsonObject;
        private String result, name;

        public EmbadImages(String name) {
            this.name = name;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                File file = new File(Environment.getExternalStorageDirectory() + "/SmartCity/Neuronet/FaceImage/IMG_" + name + ".jpg");
                file = compressFileToLessThanOneMB(file);
                Log.d("Shallu", "encodedImage = " + imageToBase64(file.getAbsolutePath()));
                jsonObject = new JSONObject();
                jsonObject.put("image", imageToBase64(file.getAbsolutePath()));
                jsonObject.put("email", "skm@gmail.com");
                String data = jsonObject.toString();
                String yourURL = "http://54.203.112.223:8000/face/embedding/";
                URL url = new URL(yourURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setFixedLengthStreamingMode(data.getBytes().length);
                connection.setRequestProperty("Content-Type", "application/json");
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
                writer.write(data);
                Log.d("Shallu", "Data to php = " + data);
                writer.flush();
                writer.close();
                out.close();
                connection.connect();

                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        in));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                result = sb.toString();
                Log.d("Shallu", "Response from php = " + result);
                //Response = new JSONObject(result);
                connection.disconnect();
            } catch (Exception e) {
                Log.d("Shallu", "Error Encountered");
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                Log.e("Result ", s);
            }
        }
    }

    private File compressFileToLessThanOneMB(File file) {
        int size = (int) (file.length() / (1024 * 1024));
        if (size >= 1) {
            try {
                file = new Compressor(getActivity()).compressToFile(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
