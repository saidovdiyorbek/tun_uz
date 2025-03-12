package dasturlash.topnews.service;

import dasturlash.topnews.repository.AttachRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AttachService {
    private final AttachRepository attachRepository;

    @Autowired
    public AttachService(AttachRepository attachRepository) {
        this.attachRepository = attachRepository;
    }

    @Value("${attach.upload.folder}")
    private String folderName;

    @Value("${attach.url}")
    private String attachUrl;

    public String openUrl(String fileName) {
        if (fileName == null) {
            return null;
        }

        if (isExist(fileName)) {
            String url = attachUrl + "/open/" + fileName;
            return url;
        }
        return null;
    }

    private Boolean isExist(String attachId) {
        if (attachId == null) return false;

        boolean exist = attachRepository.existsByIdAndVisibleTrue(attachId);
        return exist;
    }
}
