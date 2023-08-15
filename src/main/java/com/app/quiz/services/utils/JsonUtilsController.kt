package com.app.quiz.services.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api")
class JsonUtilsController
{

    // region Autowired

    @Autowired
    private lateinit var jsonUtilsService: JsonUtilsService

    // endregion

    /**
     * Keep FR questions from opendbquizz
     */
    @GetMapping("/json")
    fun filterFrenchFromJson(@RequestParam("file") file: MultipartFile): String
    {
        return this.jsonUtilsService.filterFrench(file)
    }

}