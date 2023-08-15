package com.app.quiz.services.storage

import com.app.quiz.exceptions.storage.UploadProfilePictureException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class StorageService
{

    // region Autowired

    // endregion

    private val pathUpload: Path = Paths.get("static/uploads/pp/")

    init
    {
        Files.createDirectories(pathUpload)
    }

    /**
     * Store the profile picture in the "uploads/profile_pictures" folder
     */
    fun storeProfilePicture(file: MultipartFile, userId: Long): String
    {

        val fileName = "${userId}_${file.originalFilename}"
        val targetPath = pathUpload.resolve(fileName)

        try
        {
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING)
            }
            LOGGER.info("User $userId : Upload profile picture $fileName")
        } catch (ex: Exception)
        {
            throw UploadProfilePictureException("Could not store file $fileName")
        }

        return targetPath.toString()
    }

    companion object
    {

        val LOGGER = LoggerFactory.getLogger(StorageService::class.java)
    }
}