package xyz.wongs.drunkard.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class FileUploadController {


    @GetMapping("/")
    public ModelAndView listUploadedFiles(Model model) throws IOException {
        ModelAndView mv = new ModelAndView("uploadForm");

        return mv;
    }

//    @GetMapping("/files/{filename:.+}")
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
////        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

    @PostMapping("/")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file) {
        ModelAndView mv = new ModelAndView("redirect:/");
        mv.addObject("message","You successfully uploaded " + file.getOriginalFilename() + "!");
        return mv;
    }

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }

}