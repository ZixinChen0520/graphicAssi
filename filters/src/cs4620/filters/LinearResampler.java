package cs4620.filters;

/**
 * A resampler that uses bilinear interpolation.
 *
 * @author srm
 * @author rc844
 * @author Your Name(s) Here
 */
public class LinearResampler implements ResampleEngine {

    @Override
    public void resample(SimpleImage src, SimpleImage dst, double left, double bottom, double right, double top) {
        // TODO implement linear interpolation
        int dstWidth = dst.getWidth();
        int dstHeight = dst.getHeight();
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        double scaleX = (right - left)/dstWidth;
        double scaleY = (top - bottom)/dstHeight;
        for (int iyDst = 0; iyDst < dstHeight; iyDst++) {
            for (int ixDst = 0; ixDst < dstWidth; ixDst++) {
                double xPos = (left + ixDst * scaleX) <= srcWidth - 1 ? (left + ixDst * scaleX) : srcWidth - 1;
                double yPos = (bottom + iyDst * scaleY) <= srcHeight - 1 ? (bottom + iyDst * scaleY) : srcHeight - 1;
                if (xPos < 0) xPos = 0;
                if (yPos < 0) yPos = 0;
                int leftBottom0 = src.getPixel((int) Math.floor(xPos), (int) Math.floor(yPos), 0) & 0xff;
                int leftBottom1 = src.getPixel((int) Math.floor(xPos), (int) Math.floor(yPos), 1) & 0xff;
                int leftBottom2 = src.getPixel((int) Math.floor(xPos), (int) Math.floor(yPos), 2) & 0xff;
                int rightBottom0 = src.getPixel((int) Math.min(Math.floor(xPos + 1), srcWidth-1), (int) Math.floor(yPos), 0) & 0xff;
                int rightBottom1 = src.getPixel((int) Math.min(Math.floor(xPos + 1), srcWidth-1), (int) Math.floor(yPos), 1) & 0xff;
                int rightBottom2 = src.getPixel((int) Math.min(Math.floor(xPos + 1), srcWidth-1), (int) Math.floor(yPos), 2) & 0xff;
                int leftCeil0 = src.getPixel((int) Math.floor(xPos), (int) Math.min(Math.floor(yPos + 1), srcHeight - 1), 0) & 0xff;
                int leftCeil1 = src.getPixel((int) Math.floor(xPos), (int) Math.min(Math.floor(yPos + 1), srcHeight - 1), 1) & 0xff;
                int leftCeil2 = src.getPixel((int) Math.floor(xPos), (int) Math.min(Math.floor(yPos + 1), srcHeight - 1), 2) & 0xff;
                int rightCeil0 = src.getPixel((int) Math.min(Math.floor(xPos + 1), srcWidth-1), (int) Math.min(Math.floor(yPos + 1), srcHeight - 1), 0) & 0xff;
                int rightCeil1 = src.getPixel((int) Math.min(Math.floor(xPos + 1), srcWidth-1), (int) Math.min(Math.floor(yPos + 1), srcHeight - 1), 1) & 0xff;
                int rightCeil2 = src.getPixel((int) Math.min(Math.floor(xPos + 1), srcWidth-1), (int) Math.min(Math.floor(yPos + 1), srcHeight - 1), 2) & 0xff;

                double leftBottomWeight = (Math.floor(xPos + 1) - xPos) * (Math.floor(yPos + 1) - yPos);
                double rightBottomWeight = (xPos - Math.floor(xPos)) * (Math.floor(yPos + 1) - yPos);
                double leftCeilWeight = (Math.floor(xPos + 1) - xPos) * (yPos - Math.floor(yPos));
                double rightCeilWeight = (xPos - Math.floor(xPos)) * (yPos - Math.floor(yPos));

                byte value0 = (byte) (leftBottom0 * leftBottomWeight + rightBottom0 * rightBottomWeight + leftCeil0 * leftCeilWeight + rightCeil0 * rightCeilWeight);
                byte value1 = (byte) (leftBottom1 * leftBottomWeight + rightBottom1 * rightBottomWeight + leftCeil1 * leftCeilWeight + rightCeil1 * rightCeilWeight);
                byte value2 = (byte) (leftBottom2 * leftBottomWeight + rightBottom2 * rightBottomWeight + leftCeil2 * leftCeilWeight + rightCeil2 * rightCeilWeight);
                dst.setPixel(ixDst, iyDst, 0, value0);
                dst.setPixel(ixDst, iyDst, 1, value1);
                dst.setPixel(ixDst, iyDst, 2, value2);
            }
        }

    }

}
