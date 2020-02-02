package zpo.project.fuelscanner.config;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.springframework.stereotype.Component;

import java.io.File;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;
import static org.bytedeco.javacpp.opencv_imgproc.*;

@Component
public class ReceiptScanning {
    public opencv_core.IplImage beforeSmooth(String path){
        System.out.println("jestem tutaj beforeOcr");
        final File receiptImageFile = new File(path);
        final String receiptImagePathFile = receiptImageFile.getAbsolutePath();
        System.out.println(receiptImagePathFile);
        opencv_core.IplImage receiptImage = cvLoadImage(receiptImagePathFile);
        return receiptImage;
    }
    public opencv_core.IplImage downScaleImage(opencv_core.IplImage srcImage, int percent)
    {
        System.out.println("jestem tutaj downScaleImage");
        opencv_core.IplImage destImage = cvCreateImage(
                cvSize((srcImage.width()*percent)/100,
                        (srcImage.height()*percent)/100),
                srcImage.depth(),
                srcImage.nChannels());
        cvResize(srcImage,destImage);
        File f = new File(System.getProperty("user.home")+File.separator+"afterDownScale.jpeg");
        cvSaveImage(f.getAbsolutePath(),destImage);
        return destImage;
    }
    public opencv_core.IplImage squareEdgeDetection(opencv_core.IplImage srcImage, int percent){

        System.out.println("jestem tutaj squareEdgeDetection");
        opencv_core.IplImage destImage = downScaleImage(srcImage,percent);
        opencv_core.IplImage grayImage = cvCreateImage(cvGetSize(destImage),IPL_DEPTH_8U,1);
        cvCvtColor(destImage,grayImage,CV_BGR2GRAY);
        OpenCVFrameConverter.ToMat converterToMat = new OpenCVFrameConverter.ToMat();
        Frame grayImageFrame = converterToMat.convert(grayImage);
        Mat grayImageMat = converterToMat.convert(grayImageFrame);
        GaussianBlur(grayImageMat,grayImageMat,new Size(5,5),0.0,0.0,BORDER_DEFAULT);
        destImage = converterToMat.convertToIplImage(grayImageFrame);
        cvErode(destImage,destImage);
        cvDilate(destImage,destImage);
        cvCanny(destImage,destImage,75.0,200.0);
        File f = new File(System.getProperty("user.home")+File.separator+"receipt-canny-detect.jpeg");
        cvSaveImage(f.getAbsolutePath(),destImage);
        return destImage;
    }
    public CvSeq findLargestSquare(IplImage squareEdgeDetectedImage)
    {
        IplImage foundedContoursImage = cvCloneImage(squareEdgeDetectedImage);
        CvMemStorage memory = CvMemStorage.create();
        CvSeq contours = new CvSeq();
        cvFindContours(foundedContoursImage,memory,contours, Loader.sizeof(CvContour.class),CV_RETR_LIST,CV_CHAIN_APPROX_SIMPLE,cvPoint(0,0));
        int maxWidth = 0;
        int maxHeight = 0;
        CvRect contour = null;
        CvSeq seqFounded = null;
        CvSeq nextSeq = new CvSeq();
        for(nextSeq =contours;nextSeq!=null;nextSeq=nextSeq.h_next())
        {
            contour = cvBoundingRect(nextSeq,0);
            if((contour.width()>=maxWidth)&&(contour.height()>=maxHeight))
            {
                maxWidth = contour.width();
                maxHeight = contour.height();
                seqFounded = nextSeq;

            }
        }
        CvSeq result = cvApproxPoly(seqFounded,Loader.sizeof(CvContour.class),memory,CV_POLY_APPROX_DP,cvContourPerimeter(seqFounded)*0.02,0);
        for (int i = 0; i<result.total(); i++)
        {
            CvPoint v = new CvPoint(cvGetSeqElem(result,i));
            cvDrawCircle(foundedContoursImage,v,5,CvScalar.BLUE,20,8,0);
            System.out.println("found point("+v.x()+", "+v.y()+")");

        }
        File f = new File(System.getProperty("user.home")+File.separator+"receipt-find-contours.jpeg");
        cvSaveImage(f.getAbsolutePath(),foundedContoursImage);
        return result;
    }

    public IplImage applyPerspectiveTransformThresholdOnOriginalImage(IplImage srcImage, CvSeq contour, int percent) {
        IplImage warpImage = cvCloneImage(srcImage);

        for (int i = 0; i < contour.total(); i++) {
            CvPoint point = new CvPoint(cvGetSeqElem(contour, i));
            point.x((int) (point.x() * 100) / percent);
            point.y((int) (point.y() * 100) / percent);
        }

        CvPoint topRightPoint = new CvPoint(cvGetSeqElem(contour, 0));
        CvPoint topLeftPoint = new CvPoint(cvGetSeqElem(contour, 1));
        CvPoint bottomLeftPoint = new CvPoint(cvGetSeqElem(contour, 2));
        CvPoint bottomRightPoint = new CvPoint(cvGetSeqElem(contour, 3));

        int resultWidth = (int) (topRightPoint.x() - topLeftPoint.x());
        int bottomWidth = (int) (bottomRightPoint.x() - bottomLeftPoint.x());
        if (bottomWidth > resultWidth)
            resultWidth = bottomWidth;

        int resultHeight = (int) (bottomLeftPoint.y() - topLeftPoint.y());
        int bottomHeight = (int) (bottomRightPoint.y() - topRightPoint.y());
        if (bottomHeight > resultHeight)
            resultHeight = bottomHeight;

        float[] sourcePoints = {topLeftPoint.x(), topLeftPoint.y(),
                topRightPoint.x(), topRightPoint.y(), bottomLeftPoint.x(),
                bottomLeftPoint.y(), bottomRightPoint.x(), bottomRightPoint.y()
        };

        float[] destinationPoints = {0, 0, resultWidth, 0, 0, resultHeight,
                resultWidth, resultHeight};

        CvMat homography = cvCreateMat(3, 3, CV_32FC1);
        cvGetPerspectiveTransform(sourcePoints, destinationPoints, homography);
        System.out.println(homography.toString());
        IplImage destImage = cvCloneImage(warpImage);
        cvWarpPerspective(warpImage, destImage, homography, CV_INTER_LINEAR,
                CvScalar.ZERO);
        IplImage cropping = cropImage(destImage, 0, 0, resultWidth, resultHeight);
        File f = new File(System.getProperty("user.home")+File.separator+"afterApplyadCrop.jpeg");
        cvSaveImage(f.getAbsolutePath(),cropping);
        return cropping;
    }

    public IplImage cropImage(IplImage srcImage, int fromX, int fromY,
                              int toWidth, int toHeight) {
        cvSetImageROI(srcImage, cvRect(fromX, fromY, toWidth, toHeight));
        IplImage destImage = cvCloneImage(srcImage);
        cvCopy(srcImage, destImage);
        return destImage;
    }

    public File cleanImageSmoothingForOCR(IplImage srcImage)
    {
        IplImage destImage = cvCreateImage(cvGetSize(srcImage),IPL_DEPTH_8U,1);
        cvCvtColor(srcImage,destImage,CV_BGR2GRAY);
        //cvSmooth(destImage,destImage,CV_MEDIAN,1,0,0,0);
      //  cvSmooth(destImage, destImage, CV_GAUSSIAN, 3, 1, 1, 0);
        cvThreshold(destImage,destImage,0,255,CV_THRESH_OTSU + CV_THRESH_BINARY);
        cvSmooth(destImage,destImage,CV_MEDIAN,1,0,0,0);
       // cvAdaptiveThreshold(destImage, 255,CV_ADAPTIVE_THRESH_GAUSSIAN_C, CV_THRESH_BINARY, 31);
        File f = new File(System.getProperty("user.home")+File.separator+"afterSmoothing.jpeg");
        cvSaveImage(f.getAbsolutePath(),destImage);
        return f;
    }
    public static File rotateImage(IplImage srcImage){
        IplImage destImage = cvCloneImage(srcImage);
        IplImage rotatedImage = cvCreateImage(cvGetSize(destImage), destImage.depth(), destImage.nChannels());
        CvMat matrix = cvCreateMat(2,3, CV_32FC1);

        CvPoint2D32f centerPoint = new CvPoint2D32f();
        centerPoint.x(destImage.width()/2);
        centerPoint.y(destImage.height()/2);

        cv2DRotationMatrix(centerPoint, 270, 0.6, matrix);

        cvWarpAffine(destImage, rotatedImage, matrix, CV_INTER_CUBIC + CV_WARP_FILL_OUTLIERS, cvScalar(170));
        File f = new File(System.getProperty("user.home")+File.separator+"afterRotating.jpeg");
        cvSaveImage(f.getAbsolutePath(),rotatedImage);
        return f;
    }
}
