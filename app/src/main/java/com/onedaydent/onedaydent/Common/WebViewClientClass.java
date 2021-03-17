package com.onedaydent.onedaydent.Common;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class WebViewClientClass extends WebViewClient {

    private Context mContext;
    private WebView webView;
    private ProgressBar progressBar;

    public WebViewClientClass(Context context, WebView webView, ProgressBar progressBar){
        this.mContext = context;
        this.webView = webView;
        this.progressBar = progressBar;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Intent intent;
        if(url.startsWith("tel:")){
            intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
            mContext.startActivity(intent);
            return true;
        }else if(url.startsWith("intent:")){
            view.stopLoading();
            Toast.makeText(mContext, "지원하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            view.loadUrl(url);
            return true;
        }
    }

    @TargetApi(android.os.Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
            try{
                switch(error.getErrorCode()) {
                    case ERROR_AUTHENTICATION:               // 서버에서 사용자 인증 실패
                    case ERROR_BAD_URL:                            // 잘못된 URL
                    case ERROR_CONNECT:                           // 서버로 연결 실패
                    case ERROR_FAILED_SSL_HANDSHAKE:     // SSL handshake 수행 실패
                    case ERROR_FILE:                                   // 일반 파일 오류
                    case ERROR_FILE_NOT_FOUND:                // 파일을 찾을 수 없습니다
                    case ERROR_HOST_LOOKUP:            // 서버 또는 프록시 호스트 이름 조회 실패
                    case ERROR_IO:                               // 서버에서 읽거나 서버로 쓰기 실패
                    case ERROR_PROXY_AUTHENTICATION:    // 프록시에서 사용자 인증 실패
                    case ERROR_REDIRECT_LOOP:                // 너무 많은 리디렉션
                    case ERROR_TIMEOUT:                          // 연결 시간 초과
                    case ERROR_TOO_MANY_REQUESTS:            // 페이지 로드중 너무 많은 요청 발생
                    case ERROR_UNSUPPORTED_AUTH_SCHEME:  // 지원되지 않는 인증 체계
                    case ERROR_UNSUPPORTED_SCHEME:
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    view.loadUrl("about:blank");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    builder.setMessage("네트워크 상태가 원활하지 않습니다. 잠시 후 다시 시도해 주세요.");
                    builder.show();
                }
            }catch(Exception e){

            }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        webView.scrollTo(0,0);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        progressBar.setVisibility(View.VISIBLE);
    }
}
