package com.vuchobe.api.service;

import com.vuchobe.api.dao.*;
import com.vuchobe.api.form.SearchForm;
import com.vuchobe.api.model.v2.*;
import com.vuchobe.auth.model.UserSecurity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ActivityService {

    private final ActivityDao activityDao;
    private final ActivityTypeDao activityTypeDao;
    private final AuthUserDao authUserDao;
    private final InstituteDao instituteDao;
    private final ImageDao imageDao;


    @Transactional
    public Activity save(Activity activity) {
        if (activity.getInstituteId() == null && activity.getOrganizationId() == null)
            throw new NullPointerException("Укажите кто проводит меропртиятие");
        if (activity.getTypeIds() == null) throw new NullPointerException("Выберите тип мероприятия.");
        else {
            ActivityType activityType = new ActivityType();
            if (activity.getTypeIds().size() > 0) {
                activityType.setId(activity.getTypeIds().get(0));
            }

            activity.setType(activityType);
        }
        if (activity.getInstituteId() != null) {
            Institute institute = instituteDao.findById(activity.getInstituteId())
                    .orElseThrow(() -> new NullPointerException("Институт с таким идентификатором не найден"));
            activity.setInstitute(institute);
        } else {
            throw new UnsupportedOperationException("Меропртия для организаций в данный момент не поддерживаются");
        }
        if (activity.getImagesBase64().size() > 0) {
            saveImagesToActivity(activity);
        } else {
            throw new NullPointerException("Прикрепите хотя бы 1 изображение");
        }
        return activityDao.save(activity);
    }

    @Transactional
    public Activity update(Activity activity) {
        Activity activity1Entity = activityDao.findById(activity.getId())
                .orElseThrow(() -> new NullPointerException("Мероприятие с таким идентификатором не найден"));
        activity1Entity.updateEntity(activity);
        if (activity.getImagesBase64().size() > 0) {
            saveImagesToActivity(activity);
        }
        return activityDao.save(activity1Entity);
    }

    @Transactional
    public Activity subscribeUserToActivity(Long activityId, UserSecurity userAuthEntity) {
        Optional<UserAuthEntity> user = authUserDao.findById(userAuthEntity.getId());

        if (!user.isPresent()) throw new NullPointerException("user not found by ID");

        Optional<Activity> activity = activityDao.findById(activityId);
        Activity activityEntity = activity.orElseThrow(NullPointerException::new);
        UserAuthEntity userEntity = user.orElse(null);

        userEntity.getActivities()
                .add(activityEntity);

        return activityEntity;
    }


    private void saveImagesToActivity(Activity activity) {
        Set<Image> images = new HashSet<>();
        activity.getImagesBase64().forEach(image -> {
            Image imageEntity = new Image(image);
            imageEntity.setActivity(activity);
            images.add(imageEntity);
        });
        activity.setImages(images);
        imageDao.saveAll(images);
    }

    public Page<Activity> get(SearchForm searchForm, Pageable pageable) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        if (contextHolder != null && searchForm.getIsSubscribe() != null) {
           UserSecurity userSecurity = (UserSecurity) contextHolder.getPrincipal();
           if (searchForm.getIsSubscribe()) {
            return activityDao.findAllByActivityUser(userSecurity.getId(), pageable);
           }
        }
        
        return activityDao.findAll(pageable);
    }

    public Page<ActivityType> getType(Pageable pageable) {
        return activityTypeDao.findAll(pageable);
    }

    public ActivityType saveType(ActivityType activityType) {
        return activityTypeDao.save(activityType);
    }
}
