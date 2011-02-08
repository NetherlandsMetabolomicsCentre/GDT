/**
 * FileService Service
 * 
 * Contains methods for file uploads
 *
 * @author	Robert Horlings
 * @since	20100521
 * @package	dbnp.studycapturing
 *
 * Revision information:
 * $Rev: 1430 $
 * $Author: work@osx.eu $
 * $Date: 2011-01-21 21:05:36 +0100 (Fri, 21 Jan 2011) $
 */
package org.dbnp.gdt

import org.codehaus.groovy.grails.commons.ApplicationHolder

class FileService implements Serializable {
    // ApplicationContext applicationContext

    // Must be false, since the webflow can't use a transactional service. See
    // http://www.grails.org/WebFlow for more information
    static transactional = false

    /**
     * Returns the directory for uploading files. Makes it easy to change the
     * path to the directory, if needed
     */
    def File getUploadDir() {
        // Find the absolute path to the fileuploads directory. This code is found on
        // http://stackoverflow.com/questions/491067/how-to-find-the-physical-path-of-a-gsp-file-in-a-deployed-grails-application
        //
        // The code used in NMC-DSP didn't work, because that code only works in
        // controllers:
        //    grailsAttributes.getApplicationContext().getResource("/fileuploads").getFile();
        return ApplicationHolder.application.parentContext.getResource("fileuploads").getFile()
    }

    /**
     * Returns as File object to a given file
     */
    def File get( String filename ) {
        return new File( getUploadDir(), filename );
    }

    /**
     * Check whether the given file exists in the upload directory
     */
    def boolean fileExists( String filename ) {
        return new File( getUploadDir(), filename ).exists();
    }

    /**
     * Deletes a file in the upload dir, if it exists
     */
    def boolean delete( String filename ) {
        def f = new File( getUploadDir(), filename );
        if( f.exists() ) {
            f.delete();
        }
    }

    /**
     * Moves the given file to the upload directory.
     *
     * @return Filename given to the file on our system or "" if the moving fails
     */
    def String moveFileToUploadDir( File file, String originalFilename ) {
        try {
            if( file.exists() ) {
                def newFilename = getUniqueFilename( originalFilename );
                file.renameTo( new File( getUploadDir(), newFilename ))
                return newFilename
            } else {
                return "";
            }
        } catch(Exception exception) {
            throw exception; // return ""
        }
    }

    /**
     * Moves the given uploaded file to the upload directory
     *
     * MultipartFile is the class used for uploaded files
     *
     * @return Filename given to the file on our system or "" if the moving fails
     */
    def String moveFileToUploadDir( org.springframework.web.multipart.MultipartFile file, String originalFilename ) {
        try {
            def newFilename = getUniqueFilename( originalFilename );
            file.transferTo( new File( getUploadDir(), newFilename ))
            return newFilename
        } catch(Exception exception) {
            throw exception; // return ""
        }
    }

    /**
     * Moves the given file to the upload directory.
     *
     * @return Filename given to the file on our system or "" if the moving fails
     */
    def String moveFileToUploadDir( File file ) {
        moveFileToUploadDir( file, file.getName() );
    }

    /**
     * Moves the given uploaded file to the upload directory
     *
     * MultipartFile is the class used for uploaded files
     *
     * @return Filename given to the file on our system or "" if the moving fails
     */
    def String moveFileToUploadDir( org.springframework.web.multipart.MultipartFile file ) {
        moveFileToUploadDir( file, file.getOriginalFilename() );
    }

    /**
     * Returns a filename that looks like the originalFilename and does not yet
     * exist in the upload directory. 
     *
     * @return String filename that does not yet exist in the upload directory
     */
    def String getUniqueFilename( String originalFilename ) {
        if( fileExists( originalFilename ) ) {
            def basename;
            def extension;
            
            // Split the filename into basename and extension
            if( originalFilename.lastIndexOf('.') >= 0 ) {
                basename = originalFilename[ 0 .. originalFilename.lastIndexOf('.') - 1 ];
                extension = originalFilename[ originalFilename.lastIndexOf('.')..originalFilename.size() - 1];
            } else {
                basename = originalFilename;
                extension = '';
            }

            // Find a filename that does not yet exist
            def postfix = 0;
            def newFilename = basename + postfix + extension;
            while( fileExists( newFilename ) ) {
                postfix++;
                newFilename = basename + postfix + extension;
            }

            return newFilename;
        } else {
            return originalFilename;
        }
    }
}