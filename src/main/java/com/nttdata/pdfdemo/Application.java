package com.nttdata.pdfdemo;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

@SpringBootApplication
public class Application implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		//Create PDF using Itext approach
		createItextPDF();

		//Create PDF using PDFBox approach
		createPDFBox();
	}

	private void createPDFBox() throws IOException, URISyntaxException {
		/*----------------------------Hello World Example-------------------------------------*/
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		contentStream.setFont(PDType1Font.COURIER, 12);
		contentStream.beginText();
		contentStream.showText("Hello World");
		contentStream.endText();
		contentStream.close();

		document.save("pdfBoxHelloWorld.pdf");
		document.close();


		/*---------------------------Image Example--------------------------------------------*/
		document = new PDDocument();
		page = new PDPage();
		document.addPage(page);

		Path path = Paths.get("C:/Programs/SmallLogo.scale-100.png");
		contentStream = new PDPageContentStream(document, page);
		PDImageXObject image 
		= PDImageXObject.createFromFile(path.toAbsolutePath().toString(), document);
		contentStream.drawImage(image, 0, 0);
		contentStream.close();

		document.save("pdfBoxImage.pdf");
		document.close();
	}

	private void createItextPDF() throws DocumentException, URISyntaxException,
	MalformedURLException, IOException {
		/*----------------------------Hello World Example-------------------------------------*/
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));
		document.open();
		Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
		Chunk chunk = new Chunk("Hello World", font);

		document.add(chunk);
		document.close();


		/*---------------------------Image Example--------------------------------------------*/
		Path path = Paths.get("C:/Programs/SmallLogo.scale-100.png");

		document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream("iTextImageExample.pdf"));
		document.open();
		Image img = Image.getInstance(path.toAbsolutePath().toString());
		document.add(img);

		document.close();
	}

}
