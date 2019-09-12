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
        int iDst,jDst,mflt,nflt,kflt;
        float tempX0 = 0F;
        float tempX1 = 0F;
        float tempX2 = 0F;
        float tempY0 = 0F;
        float tempY1 = 0F;
        float tempY2 = 0F;

        for (iDst = 0; iDst < dstWidth; iDst++){
            for (jDst = 0; jDst < dstHeight; jDst++){
                float [] filterResultTemp0 = new float[filterSize];
                float [] filterResultTemp1 = new float[filterSize];
                float [] filterResultTemp2 = new float[filterSize];
                for (mflt = 0; mflt < filterSize; mflt++){
                    tempX0 = 0F; tempX1 = 0F; tempX2 = 0F;
                    for (nflt = 0; mflt < filterSize; nflt++){
                        tempX0 += filter.evaluate((float) (Math.ceil(left + iDst - filter.radius() + nflt) - left - iDst))
                                * src.getPixel((int)(Math.ceil(left + iDst - filter.radius()) + nflt), (int)(Math.ceil(bottom + jDst - filter.radius() + mflt)), 0);
                        tempX1 += filter.evaluate((float) (Math.ceil(left + iDst - filter.radius() + nflt) - left - iDst))
                                * src.getPixel((int)(Math.ceil(left + iDst - filter.radius()) + nflt), (int)(Math.ceil(bottom + jDst - filter.radius() + mflt)), 1);
                        tempX2 += filter.evaluate((float) (Math.ceil(left + iDst - filter.radius() + nflt) - left - iDst))
                                * src.getPixel((int)(Math.ceil(left + iDst - filter.radius()) + nflt), (int)(Math.ceil(bottom + jDst - filter.radius() + mflt)), 2);
                    }
                    filterResultTemp0[mflt] = tempX0;
                    filterResultTemp1[mflt] = tempX1;
                    filterResultTemp2[mflt] = tempX2;
                }
                tempY0 = 0F;
                tempY1 = 0F;
                tempY2 = 0F;
                for  (kflt = 0; kflt < filterSize; kflt++){
                    tempY0 += filter.evaluate(filter.radius() + kflt)
                            * filterResultTemp0[kflt];
                    tempY1 += filter.evaluate(filter.radius() + kflt)
                            * filterResultTemp1[kflt];
                    tempY2 += filter.evaluate(filter.radius() + kflt)
                            * filterResultTemp2[kflt];
                }
                dst.setPixel(iDst, jDst,0, (byte)tempY0);
                dst.setPixel(iDst, jDst,0, (byte)tempY1);
                dst.setPixel(iDst, jDst,0, (byte)tempY2);

            }
        }
    }

}
