package ricky.oknet.lifecycle;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import ricky.oknet.OkHttpUtils;

/**
 * @author ricky.yao on 2016/6/15.
 */
public enum NetLifecycleMgr {
    Instance;
    public ConcurrentHashMap<INetViewLifecycle, List<INetLifecycle>> lcMap = new ConcurrentHashMap<>();
    public static final String TAG = "OKNetLifecycleMgr";

    public synchronized void onNetBehavior(INetViewLifecycle viewLifecycle, @OKNetBehavior int behavior) {
        if (lcMap.get(viewLifecycle) != null) {
            //execute net operate
            $(viewLifecycle.toString() + "：" + lcMap.get(viewLifecycle).size() + " request");
            for (INetLifecycle iNetLifecycle : lcMap.get(viewLifecycle)) {
                iNetLifecycle.onNetBehavior(behavior);
            }
            if (behavior == OKNetBehavior.DESTROY) {
                $(viewLifecycle.toString() + "：" + "remove request list");
                lcMap.remove(viewLifecycle);
            }
        }
    }

    public synchronized void addRequest(INetViewLifecycle viewLifecycle, INetLifecycle iNetLifecycle) {
        if (lcMap.get(viewLifecycle) != null) {
            $(viewLifecycle.toString() + "：" + "has request list");
            lcMap.get(viewLifecycle).add(iNetLifecycle);
        } else {
            $(viewLifecycle.toString() + "：" + "init request list");
            List<INetLifecycle> lifecycleArrayList = new ArrayList<>();
            lifecycleArrayList.add(iNetLifecycle);
            lcMap.put(viewLifecycle, lifecycleArrayList);
        }
    }

    public void $(String msg) {
        if (OkHttpUtils.getInstance().isInnerDebug())
            Log.d(TAG, msg);
    }
}
