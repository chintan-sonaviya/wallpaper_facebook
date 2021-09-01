package com.mcrealtime.wallpaperapp.AdUtills;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdIconView;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.mcrealtime.wallpaperapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdManagerFB {


    private static AdManagerFB ourInstance;
    private AdCallBack adCallBack;

    private int delaySeconds = 25;
    private InterstitialAd fbInterstitialAd;
    private NativeAd fbNativeAd;
    InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
        /* class com.myapplication.addemo.AdsApiClass.AdManagerFB.AnonymousClass1 */

        @Override // com.facebook.ads.InterstitialAdListener
        public void onInterstitialDisplayed(Ad ad) {
        }

        @Override // com.facebook.ads.InterstitialAdListener
        public void onInterstitialDismissed(Ad ad) {
            if (AdManagerFB.this.adCallBack != null) {
                AdManagerFB.this.adCallBack.onAdDismiss("dismissed");
                AdManagerFB.this.adCallBack = null;
            }
            AdManagerFB.this.setDelay();
            AdManagerFB.this.loadAd();
        }

        @Override // com.facebook.ads.AdListener
        public void onError(Ad ad, AdError adError) {
            int errorCode = adError.getErrorCode();
            if (!(errorCode == 2000 || errorCode == 2001)) {
                switch (errorCode) {
                    case 1000:
                    case 1001:
                    case 1002:
                        break;
                    default:
                        AdManagerFB.this.isAdLoading = false;
                        AdManagerFB.this.isAdFailedToLoad = true;
                        AdManagerFB.this.isAdLoaded = false;
                        if (AdManagerFB.this.adCallBack != null) {
                            AdManagerFB.this.adCallBack.onAdDismiss("error");
                            AdManagerFB.this.adCallBack = null;
                            return;
                        }
                        return;
                }
            }
            AdManagerFB.this.isAdLoading = false;
            AdManagerFB.this.isAdFailedToLoad = true;
            AdManagerFB.this.isAdLoaded = false;
            if (AdManagerFB.this.adCallBack != null) {
                AdManagerFB.this.adCallBack.onAdDismiss("error");
                AdManagerFB.this.adCallBack = null;
            }
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                /* class com.myapplication.addemo.AdsApiClass.$$Lambda$AdManagerFB$1$UsOb9_Pep8Rsbr_zW5a4KNgLOEQ */

                public final void run() {
                    AdManagerFB.this.loadAd();                }
            }, 2000);
        }


        @Override // com.facebook.ads.AdListener
        public void onAdLoaded(Ad ad) {
            Log.e("#DEBUG", "   onAdLoaded:  ");
            AdManagerFB.this.isAdLoading = false;
            AdManagerFB.this.isAdFailedToLoad = false;
            AdManagerFB.this.isAdLoaded = true;
        }

        @Override // com.facebook.ads.AdListener
        public void onAdClicked(Ad ad) {
        }

        @Override // com.facebook.ads.AdListener
        public void onLoggingImpression(Ad ad) {
        }
    };
    private boolean isAdFailedToLoad = false;
    private boolean isAdLoaded = false;
    private boolean isAdLoading = false;
    private boolean isShowAd = true;
    private boolean isShowAllAd = true;
    LinearLayout linearLayoutLoader;

    public interface AdCallBack {
        void onAdDismiss(String str);
    }

    public static AdManagerFB getInstance() {
        if (ourInstance == null) {
            ourInstance = new AdManagerFB();
        }
        return ourInstance;
    }

    public void loadInterstitialAds(Activity context) {

        this.fbInterstitialAd = new InterstitialAd(context, context.getResources().getString(R.string.Fb_int));
        loadAd();
        this.isShowAllAd = true;
    }


    private void loadAd() {
        this.isAdLoading = true;
        InterstitialAd interstitialAd = this.fbInterstitialAd;
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(this.interstitialAdListener).build());
    }

    public void displayInterstitialAd(Activity activity, AdCallBack call) {
        this.adCallBack = call;
        try {
            if (!this.isShowAd) {
                if (this.adCallBack != null) {
                    this.adCallBack.onAdDismiss(" Ad Delay, Not Show");
                    this.adCallBack = null;
                }
            } else if (this.isAdLoading) {
                Adloder.getInstance().showLoader(activity);
                checkIsAdLoaded();
            } else if (this.isAdLoaded && this.fbInterstitialAd.isAdLoaded()) {
                Adloder.getInstance().showLoader(activity);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    /* class com.myapplication.addemo.AdsApiClass.$$Lambda$AdManagerFB$wzsUwSmFsyXQXsgi6TbGgRuWg7w */

                    public final void run() {
                        AdManagerFB.this.lambda$displayInterstitialAd$0$AdManagerFB();
                    }
                }, 1000);
            } else if (this.isAdFailedToLoad && this.adCallBack != null) {
                this.adCallBack.onAdDismiss("  Ad failed to load");
                this.adCallBack = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public /* synthetic */ void lambda$displayInterstitialAd$0$AdManagerFB() {
        Adloder.getInstance().dismissLoader();
        this.fbInterstitialAd.show();
    }

    private void checkIsAdLoaded() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            /* class com.myapplication.addemo.AdsApiClass.$$Lambda$AdManagerFB$SHtAwxlf4s0R7vlqMu1jrMrtWA8 */

            public final void run() {
                AdManagerFB.this.lambda$checkIsAdLoaded$2$AdManagerFB();
            }
        }, 2000);
    }

    public /* synthetic */ void lambda$checkIsAdLoaded$2$AdManagerFB() {
        if (this.isAdLoaded && this.fbInterstitialAd.isAdLoaded()) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                /* class com.myapplication.addemo.AdsApiClass.$$Lambda$AdManagerFB$2YDHSr6Q88pYtzPwvTP0ceCmrk */

                public final void run() {
                    AdManagerFB.this.lambda$null$1$AdManagerFB();
                }
            }, 1000);
        } else if (this.isAdFailedToLoad) {
            this.isAdFailedToLoad = false;
            Adloder.getInstance().dismissLoader();
        } else {
            checkIsAdLoaded();
        }
    }

    public /* synthetic */ void lambda$null$1$AdManagerFB() {
        Adloder.getInstance().dismissLoader();
        this.fbInterstitialAd.show();
    }


    private void setDelay() {
        this.isShowAd = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isShowAd = true;
            }
        },(delaySeconds) * 1000);
    }


    public void loadFbAdNativeWithLoader(Activity activity, FrameLayout frameLayout) {
        this.linearLayoutLoader = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.loadernative_dialog, (ViewGroup) frameLayout, false);
        loadFbNativeLarge(activity, (NativeAdLayout) frameLayout);
        frameLayout.addView(this.linearLayoutLoader);
    }

    public void loadFbNativeLarge(final Activity activity, final NativeAdLayout nativeAdLayout) {
        nativeAdLayout.setBackgroundResource(R.drawable.customadbg);
        this.fbNativeAd = new NativeAd(activity, activity.getResources().getString(R.string.Fb_native));
        this.fbNativeAd.setAdListener(new NativeAdListener() {

            @Override
            public void onMediaDownloaded(Ad ad) {
            }

            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onAdLoaded(Ad ad) {
                if (AdManagerFB.this.fbNativeAd != null && AdManagerFB.this.fbNativeAd == ad) {
                    AdManagerFB adManagerFB = AdManagerFB.this;
                    adManagerFB.inflateAd(adManagerFB.fbNativeAd, activity, nativeAdLayout);
                    AdManagerFB.this.linearLayoutLoader.setVisibility(8);
                }
            }

            @Override // com.facebook.ads.AdListener
            public void onAdClicked(Ad ad) {
            }

            @Override // com.facebook.ads.AdListener
            public void onLoggingImpression(Ad ad) {
            }
        });
        this.fbNativeAd.loadAd();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void inflateAd(NativeAd nativeAd, Activity activity, NativeAdLayout nativeAdLayout) {
        nativeAd.unregisterView();
        int i = 0;
        LinearLayout adView = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.fbnativelarge, (ViewGroup) nativeAdLayout, false);
        nativeAdLayout.addView(adView);
        LinearLayout adChoicesContainer = (LinearLayout) adView.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(activity, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);
        AdIconView nativeAdIcon = (AdIconView) adView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
        TextView sponsoredLabel = (TextView) adView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = (Button) adView.findViewById(R.id.native_ad_call_to_action);
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        ((TextView) adView.findViewById(R.id.native_ad_body)).setText(nativeAd.getAdBodyText());
        ((TextView) adView.findViewById(R.id.native_ad_social_context)).setText(nativeAd.getAdSocialContext());
        if (!nativeAd.hasCallToAction()) {
            i = 4;
        }
        nativeAdCallToAction.setVisibility(i);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);
        nativeAd.registerViewForInteraction(adView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    public void loadFbBannerWithLoader(Activity activity, LinearLayout linearLayout) {
        this.linearLayoutLoader = (LinearLayout) LayoutInflater.from(activity).inflate(R.layout.loaderbanner_dialog, (ViewGroup) linearLayout, false);
        loadFbBanner(activity, linearLayout);
        linearLayout.addView(this.linearLayoutLoader);
    }

    public void loadFbBanner(Activity activity, LinearLayout linearLayout) {
        linearLayout.setBackgroundResource(R.drawable.customadbg);
        AdView adView = new AdView(activity, activity.getResources().getString(R.string.Fb_banner), AdSize.BANNER_HEIGHT_50);
        adView.loadAd();
        adView.setAdListener(new AdListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onAdLoaded(Ad ad) {
                AdManagerFB.this.linearLayoutLoader.setVisibility(8);
            }

            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @Override
            public void onAdClicked(Ad ad) {
            }

            @Override // com.facebook.ads.AdListener
            public void onLoggingImpression(Ad ad) {
            }
        });
        linearLayout.addView(adView);
    }

}
