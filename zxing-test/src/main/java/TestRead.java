import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.qrcode.QRCodeWriter;

public class TestRead {
	public static void main(String[] args)
			throws NotFoundException, ChecksumException, FormatException, IOException, WriterException {
		Result result = readCode();

		System.out.println("QR Code text is " + result.getText());

		String text = "98376373783";

		int width = 400;
		int height = 300; 
		String imageFormat = "png";

		generateQRCode(text, width, height, imageFormat);

		width = 700;
		height = 100;
		generateBarCode(text, width, height, imageFormat);
	}

	private static Result readCode()
			throws FileNotFoundException, IOException, NotFoundException, ChecksumException, FormatException {
		InputStream barCodeInputStream = new FileInputStream(
				"/home/admin1/Desktop/barcode.png");
		BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);

		LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Reader reader = new MultiFormatReader();
		Result result = reader.decode(bitmap);
		return result;
	}

	private static void generateBarCode(String text, int width, int height, String imageFormat)
			throws WriterException, IOException, FileNotFoundException {
		BitMatrix bitMatrix1 = new Code128Writer().encode(text, BarcodeFormat.CODE_128, width, height);
		MatrixToImageWriter.writeToStream(bitMatrix1, imageFormat,
				new FileOutputStream(new File("/home/admin1/Desktop/barcode.png")));
	}

	private static void generateQRCode(String text, int width, int height, String imageFormat)
			throws WriterException, IOException, FileNotFoundException {
		BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);
		MatrixToImageWriter.writeToStream(bitMatrix, imageFormat,
				new FileOutputStream(new File("/home/admin1/Desktop/qrcode.png")));
	}
}
