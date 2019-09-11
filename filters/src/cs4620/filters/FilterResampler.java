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
 *      dst[1,1] = 1 * src[3,4] = 1.0
 *      dst[1,3] = 1 * src[3,5] = 0
 * 2. Linear interpolation:
 *      dst[0,3] = 0.0768 * src[3,4] = 0.0768                 get 0.0768 because (0.0768 = 0.12*0.64)
 *      dst[1,1] =
 *      dst[1,2] = 0.528 * src[3,4] + 0.072 * src[4,4] = 0.6
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

    }

}
