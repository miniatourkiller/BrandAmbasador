package com.gym.GoldenGym.utils;

import java.io.File;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileServices {
     /**
    * The function checks if a given file name represents a document file based on its extension.
    * 
    * @param fileName The `isFileDocument` method checks if a given `fileName` represents a document
    * file based on its file extension. The method compares the file extension of the `fileName` with a
    * predefined list of document file extensions such as `.doc`, `.docx`, `.pdf`, `.xls`, `.xlsx
    * @return The method `isFileDocument` returns a boolean value indicating whether the given
    * `fileName` represents a document file with extensions like .doc, .docx, .pdf, .xls, .xlsx, .ppt,
    * .pptx, .txt, or .csv.
    */
    public boolean isFileDocument(String fileName) {
        String[] fileExtensions = { ".doc", ".docx", ".pdf", ".xls", ".xlsx", ".ppt", ".pptx", ".txt", ".csv" };
        for (String extension : fileExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The function `isFileImage` checks if a given file name represents an image file based on its
     * extension.
     * 
     * @param fileName The `isFileImage` method checks if a given `fileName` represents an image file
     * based on its file extension. The method compares the file extension of the `fileName` with a
     * predefined list of image file extensions such as `.jpg`, `.jpeg`, `.png`, `.gif`, `.bmp`,
     * @return The method is returning a boolean value - `true` if the `fileName` ends with any of the
     * image file extensions listed in the `fileExtensions` array, and `false` otherwise.
     */
    public boolean isFileImage(String fileName) {
        String[] fileExtensions = { ".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff", ".svg", ".webp" };
        for (String extension : fileExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

/**
 * The function `fileExtensionName` extracts and returns the file extension from a given file name.
 * 
 * @param fileName The `fileExtensionName` method takes a `fileName` as input and extracts the file
 * extension from it. The file extension is the part of the file name that comes after the last dot
 * (.).
 * @return The `fileExtension` is being returned, which is the extension of the file extracted from the
 * `fileName` provided as input to the method.
 */
    public String fileExtensionName(String fileName){
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return fileExtension;
    }

/**
 * The `uploadFile` function takes a `MultipartFile` object, saves it to a specified location with a
 * given file name, and returns true if successful.
 * 
 * @param file The `file` parameter is of type `MultipartFile`, which is a representation of an
 * uploaded file received in a multipart request.
 * @param location The `location` parameter specifies the directory where the file will be uploaded.
 * @param fileName The `fileName` parameter in the `uploadFile` method represents the name that you
 * want to give to the uploaded file. It is a `String` type parameter where you can specify the desired
 * name for the file.
 * @return The method `uploadFile` is returning a boolean value `true` if the file upload is
 * successful.
 */
    public boolean uploadFile(MultipartFile file, String location, String fileName) throws Exception {
            file.transferTo(new File(location + fileName+"."+fileExtension(file)));
            return true;
    }

   /**
    * The function `fileExtension` extracts and returns the file extension from the original filename
    * of a MultipartFile object.
    * 
    * @param file MultipartFile file: Represents a file uploaded through a web form in a Spring
    * application.
    * @return The `fileExtension` method returns the file extension of the uploaded file.
    */
    public String fileExtension(MultipartFile file){
        String fileName = file.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")+1);
        return fileExtension;
    }

/**
 * The getFile function reads the content of a file located at a specified location and returns it as a
 * byte array.
 * 
 * @param location The `location` parameter in the `getFile` method represents the directory path where
 * the file is located.
 * @param fileName The `fileName` parameter in the `getFile` method is a `String` that represents the
 * name of the file that you want to retrieve.
 * @return The method `getFile` returns a byte array containing the content of the file located at the
 * specified `location` with the given `fileName`.
 */
    public byte[] getFile(String location, String fileName) throws Exception {
        File file = new File(location + fileName);
        file.setReadable(true);
        byte[] fileContent = new byte[(int) file.length()];
        java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
        fileInputStream.read(fileContent);
        fileInputStream.close();
        return fileContent;
    }


/**
 * The function `getMediaType` determines the media type based on the file extension provided.
 * 
 * @param fileName The `fileName` parameter is a `String` representing the name of a file for which you
 * want to determine the `MediaType`.
 * @return The method `getMediaType` returns a `MediaType` object based on the file extension provided
 * in the `fileName` parameter. The method uses a switch statement to determine the appropriate
 * `MediaType` based on the file extension. The returned `MediaType` corresponds to the type of media
 * associated with the file extension.
 */
    public MediaType getMediaType(String fileName) {
        MediaType mediaType = switch (fileExtensionName(fileName).toLowerCase()) { // Ensure lowercase for case insensitivity
            case "jpeg", "jpg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;
            case "webp" -> MediaType.parseMediaType("image/webp");
            case "pdf" -> MediaType.APPLICATION_PDF;
            case "doc", "docx" -> MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            case "xls" -> MediaType.parseMediaType("application/vnd.ms-excel");
            case "xlsx" -> MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            case "ppt" -> MediaType.parseMediaType("application/vnd.ms-powerpoint");
            case "pptx" -> MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
            case "txt" -> MediaType.TEXT_PLAIN;
            case "csv" -> MediaType.parseMediaType("text/csv");
            case "html", "htm" -> MediaType.TEXT_HTML;
            case "xml" -> MediaType.APPLICATION_XML;
            default -> MediaType.APPLICATION_OCTET_STREAM; // Default fallback for unknown file types
        };
        return mediaType;
    }

    public Long fileSize(MultipartFile file){
        return file.getSize();
    }

/**
 * The `deleteFile` function in Java deletes a file located at a specified location with a given file
 * name.
 * 
 * @param location The `location` parameter represents the directory path where the file is located.
 * @param fileName The `fileName` parameter in the `deleteFile` method represents the name of the file
 * that you want to delete. It is a `String` type parameter that should contain the name of the file
 * you wish to remove from the specified location.
 * @return The method is returning a boolean value, which indicates whether the file was successfully
 * deleted or not.
 */
    public boolean deleteFile(String location, String fileName) throws Exception {
        File file = new File(location + fileName);
        return file.delete();
    }
    
 /**
  * The function generates a unique file name by combining input characters, current timestamp, and a
  * random string.
  * 
  * @param chars The `chars` parameter in the `generateFileName` method is a string that will be used
  * as a prefix for the generated file name.
  * @return A string is being returned, which is a concatenation of the input `chars`, the current
  * system time in milliseconds, and a randomly generated string of length 5.
  */
    public String generateFileName(String chars){
        return chars + System.currentTimeMillis()+ RandomGenerator.generateRandomString(5);
    }

   public boolean areFilesValid(List<MultipartFile> files) {
        for (MultipartFile file : files) {
            if (fileExtension(file).equals("docx") || 
            fileExtension(file).equals("doc") || 
            fileExtension(file).equals("xlsx") || 
            fileExtension(file).equals("pptx") || 
            fileExtension(file).equals("accdb") || 
            fileExtension(file).equals("pub") || 
            fileExtension(file).equals("vsdx") || 
            fileExtension(file).equals("one") || 
            fileExtension(file).equals("msg") || 
            fileExtension(file).equals("pst") || 
            fileExtension(file).equals("mpp") || 
            fileExtension(file).equals("xps")) {
            return false;
        }
        }
        return true;
    }

    public static String realFileSize(Long size){
        if(size<1024){
            return size + " B";
        }else if(size<1024*1024){
            return String.format("%.2f", size / 1024.0) + " KB";
        }else if(size<1024*1024*1024){
            return String.format("%.2f", size / (1024.0 * 1024.0)) + " MB";
        }else{
            return String.format("%.2f", size / (1024.0 * 1024.0 * 1024.0)) + " GB";
        }
    }
}
