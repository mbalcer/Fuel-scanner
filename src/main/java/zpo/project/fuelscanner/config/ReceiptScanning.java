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
    public opencv_core.IplImage beforeOcr(String path){
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
    public IplImage cleanImageSmoothingForOCR(IplImage srcImage)
    {
        IplImage destImage = cvCreateImage(cvGetSize(srcImage),IPL_DEPTH_8U,1);
        cvCvtColor(srcImage,destImage,CV_BGR2GRAY);
        cvSmooth(destImage,destImage,CV_MEDIAN,3,0,0,0);
        cvThreshold(destImage,destImage,0,255,CV_THRESH_OTSU);
        File f = new File(System.getProperty("user.home")+File.separator+"beforeOCR.jpeg");
        cvSaveImage(f.getAbsolutePath(),destImage);
        return destImage;
    }
}
