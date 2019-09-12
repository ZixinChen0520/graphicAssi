package cs4620.filters;

/**
 * A resampler that uses an arbitrary separable filter.  Resampling is done using the 2D filter
 *    f(x) f(y)
 * where f is the provided 1D filter.  This class will use appropriately sized filters for both
 * magnification and minification.
 * 
 * @author srm
 * @author rc844
 * @author Your Name(s) Here
 */
public class FilterResampler implements ResampleEngine {

    Filter filter;

    /**
     * A new instance that uses the provided filter
     * @param filter
     */
    FilterResampler(Filter filter) {
        this.filter = filter;
    }

    @Override
    public void resample(SimpleImage src, SimpleImage dst, double left, double bottom, double right, double top) {
        // TODO upsample(interpolate) and downsample image with arbitrary filter
        int dstWidth = dst.getWidth();
        int dstHeight = dst.getHeight();
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int filterSize = (int)(2 * filter.radius());
        int iSrc,jSrc,mflt,nflt,k;

        for (iSrc = 0; iSrc < srcWidth; iSrc++){
            for (jSrc = 0; jSrc < srcHeight; jSrc++){
                float [] filter_temp = new float[filterSize];
                for (mflt = 0; mflt < filterSize; mflt++){

                }
            }
        }


    }

}
