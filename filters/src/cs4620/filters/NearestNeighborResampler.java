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
        for (int iyDst = 0; iyDst < dstHeight; iyDst++)
            for (int ixDst = 0; ixDst < dstWidth; ixDst++) {
                double xPos = (left + ixDst) % srcWidth;
                double yPos = (bottom + iyDst) % srcHeight;
                int nX = Math.min((int)Math.max(0, Math.round(xPos)), srcWidth-1);
                int nY = Math.min((int)Math.max(0, Math.round(yPos)), srcHeight-1);
                dst.setPixel(ixDst, iyDst, 0, src.getPixel(nX, nY,0));
                dst.setPixel(ixDst, iyDst, 1, src.getPixel(nX, nY, 1));
                dst.setPixel(ixDst, iyDst, 2, src.getPixel(nX, nY, 2));
//                //A trivial get/setPixel operation
//                dstData[0 + 3 * (ixDst + nxDst * iyDst)] = srcData[0 + 3 * (ixSrc + nxSrc * iySrc)];
//                dstData[1 + 3 * (ixDst + nxDst * iyDst)] = srcData[1 + 3 * (ixSrc + nxSrc * iySrc)];
//                dstData[2 + 3 * (ixDst + nxDst * iyDst)] = srcData[2 + 3 * (ixSrc + nxSrc * iySrc)];
            }
    }

}
