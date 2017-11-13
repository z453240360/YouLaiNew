package com.bm.chengshiyoutian.youlaiwang.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bm.chengshiyoutian.youlaiwang.R;
import com.bm.chengshiyoutian.youlaiwang.activity.WoDeDingDanActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import static com.bm.chengshiyoutian.youlaiwang.activity.QueRenDingDanActivity.queInstance;
import static com.bm.chengshiyoutian.youlaiwang.activity.WoDeDingDanActivity.wodeInstance;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


	
    private IWXAPI api;
	String APP_ID = "wxd5c25b7a2a3864c3";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, APP_ID );

        api.handleIntent(getIntent(), this);
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
		Toast.makeText(getApplicationContext(),"onReq",Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResp(BaseResp resp) {
//		Toast.makeText(WXPayEntryActivity.this, resp.errCode, Toast.LENGTH_SHORT).show();
//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("提示");
//			builder.setMessage("微信支付结果" +String.valueOf(resp.errCode));
//			builder.show();
//		}

		int errCode = resp.errCode;
		if(errCode == 0){
			if(queInstance != null){
				queInstance.finish();
			}
			if(wodeInstance != null){
				wodeInstance.finish();
			}
			Toast.makeText(getApplicationContext(), "支付成功", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WXPayEntryActivity.this, WoDeDingDanActivity.class));
			finish();
		}else {
			if(queInstance != null){
				queInstance.finish();
			}
			if(wodeInstance != null){
				wodeInstance.finish();
			}
			Toast.makeText(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(WXPayEntryActivity.this, WoDeDingDanActivity.class));
			finish();
		}

	}




}