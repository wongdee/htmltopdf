package com.omidbiz.htmltopdf.pdf;

import java.awt.Color;
import java.net.MalformedURLException;
import java.net.URL;

import org.commonmark.node.Link;
import org.commonmark.node.Node;

import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.omidbiz.htmltopdf.PdfHolder;

public class ITextNewLine extends ITextObject
{

    Paragraph p;

    public ITextNewLine()
    {
        
        
    }

    @Override
    public void createITextObject(Node node)
    {
        p = new Paragraph(Chunk.NEWLINE);        
        p.setFont(PdfHolder.getFont());
        p.setAlignment(Element.ALIGN_LEFT);
    }

    @Override
    public Object getITextObject()
    {
        return p;
    }

    @Override
    public void handleTextAdd(Object elm)
    {
        p.add(new Chunk((String) elm));
    }

    @Override
    public void handleAdd(Object elm)
    {
        
    }

}
