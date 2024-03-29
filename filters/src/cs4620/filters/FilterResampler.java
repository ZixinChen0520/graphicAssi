package cs4620.filters;

/**
 * A resampler that uses an arbitrary separable filter.  Resampling is done using the 2D filter
 *    f(x) f(y)
 * where f is the provided 1D filter.  This class will use appropriately sized filters for both
 * magnification and minification.
 * 
 * @author srm
 * @author rc844
 * @author zc422
 *
 * warm up:
 * 1. Nearest Neighbor:
 * dst[0,0] = 1 * src[3,3] = 1
 * dst[1,0] = 1 * src[3,3] = 1
 * dst[2,0] = 1 * src[4,4] = 1
 * dst[3,0] = 1 * src[4,4] = 1
 * dst[4,0] = 1 * src[5,5] = 0
 * dst[0,1] = 1 * src[3,3] = 1
 * dst[1,1] = 1 * src[3,3] = 1
 * dst[2,1] = 1 * src[4,4] = 1
 * dst[3,1] = 1 * src[4,4] = 1
 * dst[4,1] = 1 * src[5,5] = 0
 * dst[0,2] = 1 * src[3,3] = 1
 * dst[1,2] = 1 * src[3,3] = 1
 * dst[2,2] = 1 * src[4,4] = 1
 * dst[3,2] = 1 * src[4,4] = 1
 * dst[4,2] = 1 * src[5,5] = 0
 * dst[0,3] = 1 * src[3,3] = 0
 * dst[1,3] = 1 * src[3,3] = 0
 * dst[2,3] = 1 * src[4,4] = 0
 * dst[3,3] = 1 * src[4,4] = 0
 * dst[4,3] = 1 * src[5,5] = 0
 * dst[0,4] = 1 * src[3,3] = 0
 * dst[1,4] = 1 * src[3,3] = 0
 * dst[2,4] = 1 * src[4,4] = 0
 * dst[3,4] = 1 * src[4,4] = 0
 * dst[4,4] = 1 * src[5,5] = 0
 * 2. Linear interpolation:
 * dst[0,0] = 0.3584 * src[3,3] 0.2816 * src[3,4] = 0.64
 * dst[1,0] = 0.4928 * src[3,3] 0.0672 * src[4,3] 0.3872 * src[3,4] 0.0528 * src[4,4] = 1.0
 * dst[2,0] = 0.224 * src[3,3] 0.336 * src[4,3] 0.176 * src[3,4] 0.264 * src[4,4] = 1.0
 * dst[3,0] = 0.5152 * src[4,3] 0.4048 * src[4,4] = 0.92
 * dst[4,0] = 0.2464 * src[4,3] 0.1936 * src[4,4] = 0.44
 * dst[0,1] = 0.0512 * src[3,3] 0.5888 * src[3,4] = 0.64
 * dst[1,1] = 0.0704 * src[3,3] 0.0096 * src[4,3] 0.8096 * src[3,4] 0.1104 * src[4,4] = 1.0
 * dst[2,1] = 0.032 * src[3,3] 0.048 * src[4,3] 0.368 * src[3,4] 0.552 * src[4,4] = 1.0
 * dst[3,1] = 0.0736 * src[4,3] 0.8464 * src[4,4] = 0.92
 * dst[4,1] = 0.0352 * src[4,3] 0.4048 * src[4,4] = 0.44
 * dst[0,2] = 0.384 * src[3,4] = 0.384
 * dst[1,2] = 0.528 * src[3,4] 0.072 * src[4,4] = 0.6
 * dst[2,2] = 0.24 * src[3,4] 0.36 * src[4,4] = 0.6
 * dst[3,2] = 0.552 * src[4,4] = 0.552
 * dst[4,2] = 0.264 * src[4,4] = 0.264
 * dst[0,3] = 0.0768 * src[3,4] = 0.0768
 * dst[1,3] = 0.1056 * src[3,4] 0.0144 * src[4,4] = 0.12
 * dst[2,3] = 0.048 * src[3,4] 0.072 * src[4,4] = 0.12
 * dst[3,3] = 0.1104 * src[4,4] = 0.1104
 * dst[4,3] = 0.0528 * src[4,4] = 0.0528
 * dst[0,4] = = 0.0
 * dst[1,4] = = 0.0
 * dst[2,4] = = 0.0
 * dst[3,4] = = 0.0
 * dst[4,4] = = 0.0
 * 3.
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
