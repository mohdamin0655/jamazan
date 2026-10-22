package my.surau.jamazan;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import java.net.*;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class MainActivity extends Activity {
  private WebView web;
  private ServerSocket server;
  private volatile boolean running = true;
  private final Object lock = new Object();
  private String config = "";
  private static final String ADMIN_PIN = "684291"; // TUKAR SEBELUM DIGUNAKAN DI SURAU
  private File store;
  @Override public void onCreate(Bundle b){super.onCreate(b);
    getWindow().addFlags(128);
    store = new File(getFilesDir(), "surau-config.json");
    try { if(store.exists()) config = read(new FileInputStream(store)); }catch(Exception ignored){}
    startServer();
    web = new WebView(this); web.getSettings().setJavaScriptEnabled(true);web.getSettings().setDomStorageEnabled(true);
    web.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    web.setWebViewClient(new WebViewClient()); setContentView(web);
    web.loadUrl("http://127.0.0.1:8765/?tv=1");
  }
  private static String read(InputStream in)throws IOException{ByteArrayOutputStream out=new ByteArrayOutputStream();byte[] b=new byte[8192];int n;while((n=in.read(b))!=-1)out.write(b,0,n);return out.toString("UTF-8");}
  private String ip(){try{WifiManager w=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);return Formatter.formatIpAddress(w.getConnectionInfo().getIpAddress());}catch(Exception e){return "IP tidak tersedia";}}
  private void startServer(){new Thread(()->{try{server=new ServerSocket(8765);while(running){Socket s=server.accept();new Thread(()->handle(s)).start();}}catch(IOException ignored){} }).start();}
  private void handle(Socket s){try {s.setSoTimeout(6000);BufferedInputStream in=new BufferedInputStream(s.getInputStream());ByteArrayOutputStream hdr=new ByteArrayOutputStream();int x;while(hdr.size()<12000&&(x=in.read())!=-1){hdr.write(x);byte[] a=hdr.toByteArray();int l=a.length;if(l>=4&&a[l-4]==13&&a[l-3]==10&&a[l-2]==13&&a[l-1]==10)break;}
    String head=hdr.toString("UTF-8");String[] lines=head.split("\r\n");if(lines.length==0)return;String[] first=lines[0].split(" ");if(first.length<2)return;String method=first[0],path=first[1];int len=0;for(String line:lines)if(line.toLowerCase().startsWith("content-length:"))len=Integer.parseInt(line.substring(15).trim());
    byte[] payload;String mime="application/json; charset=utf-8";int status=200;
    if(path.startsWith("/api/config")&&method.equals("GET")){synchronized(lock){payload=config.isEmpty()?"null".getBytes(StandardCharsets.UTF_8):config.getBytes(StandardCharsets.UTF_8);}}
    else if(path.startsWith("/api/config")&&method.equals("POST")&&!head.contains("X-Admin-Pin: "+ADMIN_PIN)){status=403;payload="{\"error\":\"invalid_pin\"}".getBytes(StandardCharsets.UTF_8);}
    else if(path.startsWith("/api/config")&&method.equals("POST")&&len>0&&len<=4000000){byte[] body=new byte[len];int p=0;while(p<len){int n=in.read(body,p,len-p);if(n<0)break;p+=n;}String candidate=new String(body,0,p,StandardCharsets.UTF_8);if(p==len&&candidate.trim().startsWith("{")){synchronized(lock){config=candidate;try(FileOutputStream f=new FileOutputStream(store)){f.write(body);}}payload="{\"ok\":true}".getBytes(StandardCharsets.UTF_8);}else{status=400;payload="{}".getBytes(StandardCharsets.UTF_8);}}
    else if(path.equals("/")||path.startsWith("/?")){mime="text/html; charset=utf-8";String html=read(getAssets().open("index.html"));html=html.replace("__TV_IP__",ip());payload=html.getBytes(StandardCharsets.UTF_8);}
    else{status=404;payload="{}".getBytes(StandardCharsets.UTF_8);}
    OutputStream out=s.getOutputStream();out.write(("HTTP/1.1 "+status+" OK\r\nContent-Type: "+mime+"\r\nCache-Control: no-store\r\nAccess-Control-Allow-Origin: *\r\nContent-Length: "+payload.length+"\r\nConnection: close\r\n\r\n").getBytes(StandardCharsets.UTF_8));out.write(payload);out.flush();
  }catch(Exception ignored){}finally{try{s.close();}catch(Exception ignored){}}}
  @Override protected void onDestroy(){running=false;try{server.close();}catch(Exception ignored){}if(web!=null)web.destroy();super.onDestroy();}
}
