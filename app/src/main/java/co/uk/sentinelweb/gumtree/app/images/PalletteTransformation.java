package co.uk.sentinelweb.gumtree.app.images;

import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import com.squareup.picasso.Transformation;

/**
 * Not really a transform but just gives us a way to access the loaded bitmap */
public class PalletteTransformation implements Transformation {

    PaletteGeneratedCallBack mCallback;
    Palette mGenerated = null;

    @Override public Bitmap transform(final Bitmap source) {
      Palette.generateAsync(source, new Palette.PaletteAsyncListener() {
          public void onGenerated(Palette palette) {
             mGenerated = palette;
             if (mCallback!=null) {
                mCallback.onGenerated(getGeneratedPalette());
            }
          }
      });
    return source;
  }

  @Override public String key() { return "palette()"; }

  public Palette getGeneratedPalette(){
      return mGenerated;
  }

  public abstract class PaletteGeneratedCallBack {
      public void onGenerated(final Palette p){

      }
  }
}