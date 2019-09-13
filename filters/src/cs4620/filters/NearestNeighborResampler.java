package cs4620.filters;

/**
 * A resampler that uses nearest-neighbor interpolation.
 *
 * @author srm
 * @author Your Name(s) Here
 */
public class NearestNeighborResampler implements ResampleEngine {

    @Override
    public void resample(SimpleImage src, SimpleImage dst, double left, double bottom, double right, double top) {
        // TODO nearest-neighbor sampling
        int dstWidth = dst.getWidth();
        int dstHeight = dst.getHeight();
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        double scaleX = (right - left) / dst.getWidth();
        double scaleY = (top - bottom) / dst.getHeight();
        for (int iyDst = 0; iyDst < dstHeight; iyDst++) {
            for (int ixDst = 0; ixDst < dstWidth; ixDst++) {
                //double xPos = (left + ixDst) <= srcWidth - 1 ? (left + ixDst) : srcWidth - 1;
                //double yPos = (bottom + iyDst) <= srcHeight -1  ? (bottom + iyDst) : srcHeight - 1;
                double xPos = Math.round(left + ixDst * scaleX);
                double yPos = Math.round(bottom + iyDst * scaleY);
                int nX = Math.min((int) Math.max(0, xPos), srcWidth - 1);
                int nY = Math.min((int) Math.max(0, yPos), srcHeight - 1);
                dst.setPixel(ixDst, iyDst, 0, src.getPixel(nX, nY, 0));
                dst.setPixel(ixDst, iyDst, 1, src.getPixel(nX, nY, 1));
                dst.setPixel(ixDst, iyDst, 2, src.getPixel(nX, nY, 2));
            }
        }
    }

}
