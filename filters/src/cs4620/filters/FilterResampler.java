package cs4620.filters;

/**
 * A resampler that uses an arbitrary separable filter.  Resampling is done using the 2D filter
 * f(x) f(y)
 * where f is the provided 1D filter.  This class will use appropriately sized filters for both
 * magnification and minification.
 *
 * @author srm
 * @author rc844
 * @author zc422
 * <p>
 * warm up:
 * 1. Nearest Neighbor:
 * dst[0,0] = 1 * src[3,3] = 1
 * dst[1,0] = 1 * src[3,3] = 1
 * dst[2,0] = 1 * src[4,4] = 1
 * dst[3,0] = 1 * src[4,4] = 1
 * dst[0,1] = 1 * src[3,3] = 1
 * dst[1,1] = 1 * src[3,3] = 1
 * dst[2,1] = 1 * src[4,4] = 1
 * dst[3,1] = 1 * src[4,4] = 1
 * dst[0,2] = 1 * src[3,3] = 1
 * dst[1,2] = 1 * src[3,3] = 1
 * dst[2,2] = 1 * src[4,4] = 1
 * dst[3,2] = 1 * src[4,4] = 1
 * 2. Linear interpolation:
 * dst[0,0] = 0.3584 * src[3,3] + 0.2816 * src[3,4] = 0.64
 * dst[1,0] = 0.4928 * src[3,3] + 0.0672 * src[4,3] + 0.3872 * src[3,4] + 0.0528 * src[4,4] = 1.0
 * dst[2,0] = 0.224 * src[3,3] + 0.336 * src[4,3] + 0.176 * src[3,4] + 0.264 * src[4,4] = 1.0
 * dst[3,0] = 0.5152 * src[4,3] + 0.4048 * src[4,4] = 0.92
 * dst[4,0] = 0.2464 * src[4,3] + 0.1936 * src[4,4] = 0.44
 * dst[0,1] = 0.0512 * src[3,3] + 0.5888 * src[3,4] = 0.64
 * dst[1,1] = 0.0704 * src[3,3] + 0.0096 * src[4,3] + 0.8096 * src[3,4] + 0.1104 * src[4,4] = 1.0
 * dst[2,1] = 0.032 * src[3,3] + 0.048 * src[4,3] + 0.368 * src[3,4] + 0.552 * src[4,4] = 1.0
 * dst[3,1] = 0.0736 * src[4,3] + 0.8464 * src[4,4] = 0.92
 * dst[4,1] = 0.0352 * src[4,3] + 0.4048 * src[4,4] = 0.44
 * dst[0,2] = 0.384 * src[3,4] = 0.384
 * dst[1,2] = 0.528 * src[3,4] + 0.072 * src[4,4] = 0.6
 * dst[2,2] = 0.24 * src[3,4] + 0.36 * src[4,4] = 0.6
 * dst[3,2] = 0.552 * src[4,4] = 0.552
 * dst[4,2] = 0.264 * src[4,4] = 0.264
 * dst[0,3] = 0.0768 * src[3,4] = 0.0768
 * dst[1,3] = 0.1056 * src[3,4] + 0.0144 * src[4,4] = 0.12
 * dst[2,3] = 0.048 * src[3,4] + 0.072 * src[4,4] = 0.12
 * dst[3,3] = 0.1104 * src[4,4] = 0.1104
 * dst[4,3] = 0.0528 * src[4,4] = 0.0528
 * 3. B-Spline Cubic
 * dst[0,0] = + 0.0000 * src[1,2] + 0.0000 * src[1,3] + 0.0000 * src[1,4] + 0.0000 * src[1,5] + 0.0000 * src[2,2] + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[3,2] + 0.2890 * src[3,3] + 0.2471 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[4,2] + 0.0225 * src[4,3] + 0.0193 * src[4,4] + 0.0000 * src[4,5] = 0.5778
 * dst[0,1] = + 0.0000 * src[1,2] + 0.0000 * src[1,3] + 0.0000 * src[1,4] + 0.0000 * src[1,5] + 0.0000 * src[2,2] + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[3,2] + 0.1175 * src[3,3] + 0.3702 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[4,2] + 0.0092 * src[4,3] + 0.0289 * src[4,4] + 0.0000 * src[4,5] = 0.5256
 * dst[0,2] = + 0.0000 * src[1,3] + 0.0000 * src[1,4] + 0.0000 * src[1,5] + 0.0000 * src[1,6] + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[2,6] + 0.0202 * src[3,3] + 0.3019 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0016 * src[4,3] + 0.0235 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] = 0.3471
 * dst[0,3] = + 0.0000 * src[1,3] + 0.0000 * src[1,4] + 0.0000 * src[1,5] + 0.0000 * src[1,6] + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[2,6] + 0.0002 * src[3,3] + 0.1306 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0000 * src[4,3] + 0.0102 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] = 0.1409
 * dst[0,4] = + 0.0000 * src[1,4] + 0.0000 * src[1,5] + 0.0000 * src[1,6] + 0.0000 * src[1,7] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[2,6] + 0.0000 * src[2,7] + 0.0245 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0000 * src[3,7] + 0.0019 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[4,7] = 0.0264
 * dst[1,0] = + 0.0000 * src[2,2] + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[3,2] + 0.3368 * src[3,3] + 0.2879 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[4,2] + 0.1201 * src[4,3] + 0.1027 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[5,2] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] = 0.8476
 * dst[1,1] = + 0.0000 * src[2,2] + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[3,2] + 0.1369 * src[3,3] + 0.4314 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[4,2] + 0.0488 * src[4,3] + 0.1539 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[5,2] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] = 0.7711
 * dst[1,2] = + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[2,6] + 0.0235 * src[3,3] + 0.3518 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0084 * src[4,3] + 0.1255 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] = 0.5092
 * dst[1,3] = + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[2,6] + 0.0002 * src[3,3] + 0.1522 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0001 * src[4,3] + 0.0543 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] = 0.2067
 * dst[1,4] = + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[2,6] + 0.0000 * src[2,7] + 0.0285 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0000 * src[3,7] + 0.0102 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[4,7] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] + 0.0000 * src[5,7] = 0.0387
 * dst[2,0] = + 0.0000 * src[2,2] + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[3,2] + 0.2138 * src[3,3] + 0.1828 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[4,2] + 0.2778 * src[4,3] + 0.2375 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[5,2] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] = 0.9119
 * dst[2,1] = + 0.0000 * src[2,2] + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[3,2] + 0.0869 * src[3,3] + 0.2739 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[4,2] + 0.1129 * src[4,3] + 0.3558 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[5,2] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] = 0.8295
 * dst[2,2] = + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[2,6] + 0.0149 * src[3,3] + 0.2234 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0194 * src[4,3] + 0.2902 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] = 0.5478
 * dst[2,3] = + 0.0000 * src[2,3] + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[2,6] + 0.0001 * src[3,3] + 0.0966 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0002 * src[4,3] + 0.1255 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] = 0.2224
 * dst[2,4] = + 0.0000 * src[2,4] + 0.0000 * src[2,5] + 0.0000 * src[2,6] + 0.0000 * src[2,7] + 0.0181 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0000 * src[3,7] + 0.0235 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[4,7] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] + 0.0000 * src[5,7] = 0.0417
 * dst[3,0] = + 0.0000 * src[3,2] + 0.0669 * src[3,3] + 0.0572 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[4,2] + 0.3406 * src[4,3] + 0.2912 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[5,2] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[6,2] + 0.0000 * src[6,3] + 0.0000 * src[6,4] + 0.0000 * src[6,5] = 0.7560
 * dst[3,1] = + 0.0000 * src[3,2] + 0.0272 * src[3,3] + 0.0857 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[4,2] + 0.1385 * src[4,3] + 0.4363 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[5,2] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[6,2] + 0.0000 * src[6,3] + 0.0000 * src[6,4] + 0.0000 * src[6,5] = 0.6877
 * dst[3,2] = + 0.0047 * src[3,3] + 0.0699 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0238 * src[4,3] + 0.3558 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] + 0.0000 * src[6,3] + 0.0000 * src[6,4] + 0.0000 * src[6,5] + 0.0000 * src[6,6] = 0.4542
 * dst[3,3] = + 0.0000 * src[3,3] + 0.0302 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0002 * src[4,3] + 0.1539 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] + 0.0000 * src[6,3] + 0.0000 * src[6,4] + 0.0000 * src[6,5] + 0.0000 * src[6,6] = 0.1844
 * dst[3,4] = + 0.0057 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0000 * src[3,7] + 0.0289 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[4,7] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] + 0.0000 * src[5,7] + 0.0000 * src[6,4] + 0.0000 * src[6,5] + 0.0000 * src[6,6] + 0.0000 * src[6,7] = 0.0345
 * dst[4,0] = + 0.0000 * src[3,2] + 0.0073 * src[3,3] + 0.0063 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[4,2] + 0.2273 * src[4,3] + 0.1944 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[5,2] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[6,2] + 0.0000 * src[6,3] + 0.0000 * src[6,4] + 0.0000 * src[6,5] = 0.4353
 * dst[4,1] = + 0.0000 * src[3,2] + 0.0030 * src[3,3] + 0.0094 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[4,2] + 0.0924 * src[4,3] + 0.2912 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[5,2] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[6,2] + 0.0000 * src[6,3] + 0.0000 * src[6,4] + 0.0000 * src[6,5] = 0.3960
 * dst[4,2] = + 0.0005 * src[3,3] + 0.0076 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0159 * src[4,3] + 0.2375 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] + 0.0000 * src[6,3] + 0.0000 * src[6,4] + 0.0000 * src[6,5] + 0.0000 * src[6,6] = 0.2615
 * dst[4,3] = + 0.0000 * src[3,3] + 0.0033 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0001 * src[4,3] + 0.1027 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[5,3] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] + 0.0000 * src[6,3] + 0.0000 * src[6,4] + 0.0000 * src[6,5] + 0.0000 * src[6,6] = 0.1062
 * dst[4,4] = + 0.0006 * src[3,4] + 0.0000 * src[3,5] + 0.0000 * src[3,6] + 0.0000 * src[3,7] + 0.0193 * src[4,4] + 0.0000 * src[4,5] + 0.0000 * src[4,6] + 0.0000 * src[4,7] + 0.0000 * src[5,4] + 0.0000 * src[5,5] + 0.0000 * src[5,6] + 0.0000 * src[5,7] + 0.0000 * src[6,4] + 0.0000 * src[6,5] + 0.0000 * src[6,6] + 0.0000 * src[6,7] = 0.0199
 */
public class FilterResampler implements ResampleEngine {

    Filter filter;

    /**
     * A new instance that uses the provided filter
     *
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
        double filterSizeX = filter.radius() * (srcWidth / dstWidth) < 1 ? filter.radius() : filter.radius() * (srcWidth / dstWidth);
        double filterSizeY = filter.radius() * (srcHeight / dstHeight) < 1 ? filter.radius() : filter.radius() * (srcHeight / dstHeight);
        double scaleX = (right - left) / dst.getWidth();
        double scaleY = (top - bottom) / dst.getHeight();
        for (int iyDst = 0; iyDst < dstHeight; iyDst++) {
            for (int ixDst = 0; ixDst < dstWidth; ixDst++) {
                //double xPos = (left + ixDst) <= srcWidth - 1 ? (left + ixDst) : srcWidth - 1;
                //double yPos = (bottom + iyDst) <= srcHeight -1  ? (bottom + iyDst) : srcHeight - 1;
                double xPos = left + ixDst * scaleX;
                double yPos = bottom + iyDst * scaleY;
                int i;
                int j;
                double value0 = 0, value1 = 0, value2 = 0;

                for (i = (int) Math.ceil(xPos - filterSizeX); i <= Math.floor(xPos + filterSizeX); i++) {
                    for (j = (int) Math.ceil(yPos - filterSizeY); j <= Math.floor(yPos + filterSizeY); j++) {
                        int tempi = Math.max(0, Math.min(i, srcWidth - 1));
                        int tempj = Math.max(0, Math.min(j, srcHeight - 1));
                        value0 += (double) filter.evaluate((float) (i - xPos)) * (double) filter.evaluate((float) (j - yPos))
                                * (src.getPixel(tempi, tempj, 0) & 0xff);
                        value1 += (double) filter.evaluate((float) (i - xPos)) * (double) filter.evaluate((float) (j - yPos))
                                * (src.getPixel(tempi, tempj, 1) & 0xff);
                        value2 += (double) filter.evaluate((float) (i - xPos)) * (double) filter.evaluate((float) (j - yPos))
                                * (src.getPixel(tempi, tempj, 2) & 0xff);
                    }
                }
                value0 = Math.min(255, Math.max(0, value0));
                value1 = Math.min(255, Math.max(0, value1));
                value2 = Math.min(255, Math.max(0, value2));
                dst.setPixel(ixDst, iyDst, 0, (byte) value0);
                dst.setPixel(ixDst, iyDst, 1, (byte) value1);
                dst.setPixel(ixDst, iyDst, 2, (byte) value2);
            }
        }
    }

}
