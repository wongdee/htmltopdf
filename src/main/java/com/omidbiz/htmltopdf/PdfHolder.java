package com.omidbiz.htmltopdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.MultiColumnText;
import com.lowagie.text.pdf.PdfWriter;

/**
 * @author Omid Pourhadi
 *         <p>
 *         this class is immutable
 *         </p>
 */
public class PdfHolder
{

    public static final int DEFAULT_FONT_SIZE = 12;

    private final Document document;
    private final PdfWriter pdfWriter;
    private final BaseFont bf;
    private MultiColumnText multiColumnText;
    private List<ChunkHolder> chunks = new ArrayList<>(0);

    public PdfHolder() throws URISyntaxException, DocumentException, IOException
    {
        document = new Document();

        Path path = Paths.get(ToPdfSerializer.class.getResource("/tahoma.ttf").toURI());
        bf = BaseFont.createFont(path.toFile().getAbsolutePath(), BaseFont.IDENTITY_H, true);
        pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(new File("/home/omidp/1.pdf")));
        pdfWriter.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
    }

    public void open()
    {
        document.open();
    }

    public void close()
    {
        document.close();
    }

    public Font getFont()
    {
        return new Font(bf, DEFAULT_FONT_SIZE);
    }

    public PdfWriter getPdfWriter()
    {
        return pdfWriter;
    }

    public Paragraph getParagraph()
    {
        Paragraph p = new Paragraph();
        p.setFont(getFont());
        p.setAlignment(Element.ALIGN_LEFT);
        return p;
    }

    public Chunk getChunk()
    {
        Chunk c = new Chunk();
        c.setFont(getFont());
        return c;
    }

    public MultiColumnText getMultiColumnText()
    {
        multiColumnText = new MultiColumnText();
        multiColumnText.setAlignment(Element.ALIGN_LEFT);
        multiColumnText.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        multiColumnText.setColumnsRightToLeft(true);
        multiColumnText.addSimpleColumn(document.left(), document.right());
        return multiColumnText;
    }

    public MultiColumnText addToColumnText(Element elm)
    {
        MultiColumnText mt = getMultiColumnText();
        try
        {
            mt.addElement(elm);
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        return mt;
    }

    public void addToDocument(Element elm)
    {
        try
        {
            document.add(elm);
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
    }

    public void addChunk(ChunkHolder c)
    {
        chunks.add(c);
    }

    public Collection<ChunkHolder> getChunks()
    {
        return Collections.unmodifiableCollection(chunks);
    }

    public static class ChunkHolder
    {
        private Chunk chunk;
        private boolean createNewParagraph;
        private boolean createNewMultiColumnText;
        private PdfTextType pdfTextType;

        

        public ChunkHolder(Chunk chunk, boolean createNewParagraph, boolean createNewMultiColumnText, PdfTextType pdfTextType)
        {
            this.chunk = chunk;
            this.createNewParagraph = createNewParagraph;
            this.createNewMultiColumnText = createNewMultiColumnText;
            this.pdfTextType = pdfTextType;
        }

        public Chunk getChunk()
        {
            return chunk;
        }

        

        public boolean isCreateNewParagraph()
        {
            return createNewParagraph;
        }

        public boolean isCreateNewMultiColumnText()
        {
            return createNewMultiColumnText;
        }

        public PdfTextType getPdfTextType()
        {
            return pdfTextType;
        }

    }

    public enum PdfTextType {
        StrongEmphasis, SoftLineBreak, H1, H2, PARAGRAPH, NO_MORE_TEXT, Emphasis;
    }

}