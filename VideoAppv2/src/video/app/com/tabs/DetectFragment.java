package video.app.com.tabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.exemplo.videoapp.R;

import video.app.autobahn.WebSocketConnection;
import video.app.autobahn.WebSocketConnectionHandler;
import video.app.autobahn.WebSocketException;
import video.app.autobahn.WebSocket;
import video.app.com.util.Util;
import video.app.com.websocket.WebSocketMain;

/**
 *
 */
public class DetectFragment extends SherlockFragment implements OnClickListener {

	private View v;
	private WebSocket mConnection = WebSocketMain.getInstance().getWSConnection();
	static final String TAG = "video.app.com.tabs.DetectFragment";
	private Util util;
	private ToggleButton tbtn1;
	private ToggleButton tbtn2;
	private ImageView imgViewer1;
	private String wsuri;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		v = inflater.inflate(R.layout.tab_detect, container, false);
        
		util = new Util(this.getActivity());
		init();
		
		return v;
	}
	
	private void init() {
		
		tbtn1 = (ToggleButton)v.findViewById(R.id.tbtnDetect1);
		tbtn1.setOnClickListener(this);
		
		tbtn2 = (ToggleButton)v.findViewById(R.id.tbtnDetect2);
		tbtn2.setOnClickListener(this);
	}
	
	private void start() {

      try {

         mConnection.connect(wsuri, new WebSocketConnectionHandler() {
        	 
            @Override
            public void onOpen() {
            	mConnection.sendTextMessage("Android app ws established.");
            }

            @Override
            public void onTextMessage(String payload) {
            	Log.i(TAG, "from websocket server: " + payload);
            }
            
            @Override
            public void onBinaryMessage(byte[] payload){
            	try{
            		imgViewer1 = (ImageView) getActivity().findViewById(R.id.imgViewDetect);
            		DisplayMetrics dm = new DisplayMetrics();
                    Bitmap bm = BitmapFactory.decodeByteArray(payload, 0, payload.length);
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);

                    imgViewer1.setMinimumHeight(dm.heightPixels);
                    imgViewer1.setMinimumWidth(dm.widthPixels);
                    imgViewer1.setImageBitmap(bm);
            	}
            	catch(Exception e){
            		Log.e(TAG, e.getMessage());
            	}
            }

            @Override
            public void onClose(int code, String reason) {

            }
         });
         
      } catch (WebSocketException e) {
         Log.d(TAG, e.toString());
      }
      
   }
	
	
	@Override
	public void onClick(View arg0) {
		switch(arg0.getId()) {
			case R.id.tbtnDetect1:
				if(tbtn1.isChecked()){
					tbtn2.setEnabled(false);
					
					Log.d(TAG, "toggle button1 is on");
					wsuri = "ws://" + util.getIP() + "/videotracking/detection1";
					start();
					//////////////////////////////////////
					//need time to establish websocket connection.
					//need to put a few waiting time, otherwise will have error.
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					///////////////////////////////////////
					Thread thread = new Thread(){
						public void run(){
							while(tbtn1.isChecked()){
								try {
									mConnection.sendTextMessage("Android app waiting for image.");
									Thread.sleep(100);
								} catch (Exception e) {
									Log.e(TAG, e.getMessage());
								}
							}
						}
					};
					thread.start();
				}
				else{
					Log.d(TAG, "toggle button1 is off");
					mConnection.disconnect();
					tbtn2.setEnabled(true);
				}
				break;
			case R.id.tbtnDetect2:
				if(tbtn2.isChecked()){
					tbtn1.setEnabled(false);
					
					Log.d(TAG, "toggle button2 is on");
					wsuri = "ws://" + util.getIP() + "/videotracking/detection2";
					start();
					//////////////////////////////////////
					//need time to establish websocket connection.
					//need to put a few waiting time, otherwise will have error.
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					///////////////////////////////////////
					Thread thread = new Thread(){
						public void run(){
							while(tbtn2.isChecked()){
								try {
									mConnection.sendTextMessage("Android app waiting for image.");
									Thread.sleep(100);
								} catch (Exception e) {
									Log.e(TAG, e.getMessage());
								}
							}
						}
					};
					thread.start();
				}
				else{
					Log.d(TAG, "toggle button is off");
					mConnection.disconnect();
					tbtn1.setEnabled(true);
				}
				break;
		}
	}
}
