package com.springinpractice.ch12.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.transform.stream.StreamSource;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.springinpractice.ch12.model.Article;
import com.springinpractice.ch12.model.ArticlePage;

import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import jakarta.xml.bind.Unmarshaller;

@Component
public class ArticleConverter implements Converter<MultipartFile, Article>, ServletContextAware {
    
    private static final int BUFFER_SIZE = 4096;
    
    @Inject 
    private Unmarshaller unmarshaller;
    
    private ServletContext servletContext;
    private Random random = new Random();
    
    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
    
    @Override
    public Article convert(MultipartFile zipFile) {
        try {
            File tempDir = createTempDir();
            unzip(zipFile, tempDir);
            Article article = assembleArticle(tempDir);
            return article;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private File createTempDir() {
        File tempDir = (File) servletContext.getAttribute("jakarta.servlet.context.tempdir");
        if (tempDir == null) {
            tempDir = new File(System.getProperty("java.io.tmpdir"));
        }
        File articleDir;
        int count = 0;
        do {
            articleDir = new File(tempDir, "article-" + Math.abs(random.nextLong()));
            if (count++ > 5) {
                throw new RuntimeException("Can't create a temporary directory. Something is very wrong.");
            }
        } while (articleDir.exists());
        
        articleDir.mkdirs();
        return articleDir;
    }
    
    private void unzip(MultipartFile zipFile, File destDir) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipFile.getBytes()));
        ZipEntry entry;
        while ((entry = zis.getNextEntry()) != null) {
            File entryFile = new File(destDir, entry.getName());
            if (entry.isDirectory()) {
                entryFile.mkdirs();
            } else {
                File parent = entryFile.getParentFile();
                if (parent != null) {
                    parent.mkdirs();
                }
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(entryFile), BUFFER_SIZE);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    bos.write(buffer, 0, len);
                }
                bos.flush();
                bos.close();
            }
        }
        zis.close();
    }
    
    private Article assembleArticle(File articleDir) throws Exception {
        File articleFile = new File(articleDir, "article.xml");
        StreamSource articleSrc = new StreamSource(articleFile);
        Article article = (Article) unmarshaller.unmarshal(articleSrc);
        
        List<ArticlePage> pages = article.getPages();
        File pageFile;
        int pageNumber = 1;
        
        while ((pageFile = new File(articleDir, pageNumber + ".html")).exists()) {
            StringBuilder builder = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(pageFile));
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line).append('\n');
            }
            br.close();
            
            String htmlPage = builder.toString();
            int startIndex = htmlPage.indexOf("<body>");
            int endIndex = htmlPage.indexOf("</body>");
            
            if (startIndex == -1 || endIndex == -1) {
                throw new RuntimeException("Invalid HTML page: " + pageFile + " must have <body> and </body> tags.");
            }
            
            startIndex += 6; 
            String content = htmlPage.substring(startIndex, endIndex);
            
            ArticlePage page = new ArticlePage();
            page.setContent(content);
            pages.add(page);
            
            pageNumber++;
        }
        return article;
    }
}