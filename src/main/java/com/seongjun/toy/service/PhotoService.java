package com.seongjun.toy.service;

import com.seongjun.toy.domain.Photo;
import com.seongjun.toy.repository.PhotoRepository;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhotoService {
    @Value("${file.dir}")
    private String photoPath;

    private final PhotoRepository photoRepository;

    public Photo registerPhoto(MultipartFile files) throws IOException {
        if (files.isEmpty()) {
            throw new NotFoundException("파일이 없어욧!!");
        }

        //원래 파일 이름 추출
        String originalPhotoName = files.getOriginalFilename();

        //파일 이름으로 쓸 uuid 생성
        String uuid = UUID.randomUUID().toString();

        //확장자 추출
        String extension = originalPhotoName.substring(originalPhotoName.indexOf("."));

        //uuid + 확장자
        String savedPhotoName= uuid + extension;

        //파일을 불러올 때 사용할 경로
        String savedPath = photoPath + savedPhotoName;

        Photo photo = new Photo();
        photo.setOriginalPhotoName(originalPhotoName);
        photo.setSavedPhotoName(savedPhotoName);
        photo.setSavedPath(savedPath);

        //실제 로컬에 uuid를 파일명으로 저장
        files.transferTo(new File(savedPath));

        //DB에 파일 정보 저장
        return photoRepository.save(photo);
    }


}
