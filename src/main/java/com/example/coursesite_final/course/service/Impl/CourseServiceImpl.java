package com.example.coursesite_final.course.service.Impl;

import com.example.coursesite_final.course.dto.CourseDto;
import com.example.coursesite_final.course.dto.InputCourseDto;
import com.example.coursesite_final.course.entity.Course;
import com.example.coursesite_final.course.entity.TakeCourse;
import com.example.coursesite_final.course.mapper.CourseMapper;
import com.example.coursesite_final.course.model.CourseParam;
import com.example.coursesite_final.course.model.ServiceResult;
import com.example.coursesite_final.course.model.TakeCourseInput;
import com.example.coursesite_final.course.repository.CourseRepository;
import com.example.coursesite_final.course.repository.TakeCourseRepository;
import com.example.coursesite_final.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TakeCourseRepository takeCourseRepository;
    private final CourseMapper courseMapper;

    private LocalDate getLocalDate(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(value, formatter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void add(InputCourseDto courseDto) {
        LocalDate saleEndAt = getLocalDate(courseDto.getSaleEndDt());
        Course course = Course.builder()
                .categoryId(courseDto.getCategoryId())
                .subject(courseDto.getSubject())
                .keyword(courseDto.getKeyword())
                .summary(courseDto.getSummary())
                .contents(courseDto.getContents())
                .price(courseDto.getPrice())
                .salePrice(courseDto.getSalePrice())
                .saleEndDt(saleEndAt)
                .registeredAt(LocalDateTime.now())
                .filename(courseDto.getFilename())
                .urlFilename(courseDto.getUrlFilename())
                .build();
        courseRepository.save(course);
    }

    @Override
    public boolean set(InputCourseDto courseDto) {
        LocalDate saleEndAt = getLocalDate(courseDto.getSaleEndDt());
        Optional<Course> optionalCourse = courseRepository.findById(courseDto.getId());
        if (!optionalCourse.isPresent()) { // 수정할 데이터가 없는 경우
            return false;
        }
        Course course = optionalCourse.get();
        course.setCategoryId(courseDto.getCategoryId())
                .setSubject(courseDto.getSubject())
                .setKeyword(courseDto.getKeyword())
                .setSummary(courseDto.getSummary())
                .setContents(courseDto.getContents())
                .setPrice(courseDto.getPrice())
                .setSalePrice(courseDto.getSalePrice())
                .setPrice(courseDto.getPrice())
                .setSaleEndDt(saleEndAt)
                .setUpdatedAt(LocalDateTime.now())
                .setFilename(courseDto.getFilename())
                .setUrlFilename(courseDto.getUrlFilename());
        courseRepository.save(course);
        return true;
    }

    @Override
    public List<CourseDto> list(CourseParam param) {
        long totalCount = courseMapper.selectListCount(param);

        List<CourseDto> list = courseMapper.selectList(param);
        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (CourseDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - param.getPageStart() - i);
                i++;
            }
        }
        return list;
    }
    @Override
    public CourseDto getById(long id) {
        return courseRepository.findById(id).map(CourseDto::fromEntity).orElse(null);
    }


    @Override
    public void delete(String idList) {
        if (idList != null && idList.length() > 0) {
            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (id > 0) {
                    courseRepository.deleteById(id);
                }
            }
        }
    }

    @Override
    public List<CourseDto> frontList(CourseParam parameter) {
        if (parameter.getCategoryId() < 1) {
            List<Course> courseList = courseRepository.findAll();
            return CourseDto.fromEntity(courseList);
        }

        Optional<List<Course>> optionalCourses = courseRepository.findByCategoryId(parameter.getCategoryId());
        if (optionalCourses.isPresent()) {
            return CourseDto.fromEntity(optionalCourses.get());
        }
        return null;
    }

    @Override
    public CourseDto frontDetail(long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            return CourseDto.fromEntity(optionalCourse.get());
        }
        return null;
    }

    @Override
    public ServiceResult req(TakeCourseInput courseInput) {
        ServiceResult result = new ServiceResult();

        Optional<Course> optionalCourse = courseRepository.findById(courseInput.getCourseId());
        if (!optionalCourse.isPresent()) {
            result.setResult(false);
            result.setMessage("강좌 정보가 존재하지 않습니다.");
            return result;
        }

        Course course = optionalCourse.get();

        //이미 신청정보가 있는지 확인
        String[] statusList = {TakeCourse.STATUS_REQ, TakeCourse.STATUS_COMPLETE};
        long count = takeCourseRepository.countByCourseIdAndUserIdAndStatusIn(course.getId(), courseInput.getUserId(), Arrays.asList(statusList));

        if (count > 0) {
            result.setResult(false);
            result.setMessage("이미 신청한 강좌 정보가 존재합니다.");
            return result;
        }

        TakeCourse takeCourse = TakeCourse.builder()
                .courseId(course.getId())
                .userId(courseInput.getUserId())
                .payPrice(course.getSalePrice())
                .registeredAt(LocalDateTime.now())
                .status(TakeCourse.STATUS_REQ)
                .build();
        takeCourseRepository.save(takeCourse);

        result.setResult(true);
        result.setMessage("");
        return result;
    }

    @Override
    public List<CourseDto> listAll() {
        List<Course> courseList = courseRepository.findAll();
        return CourseDto.fromEntity(courseList);
    }
}
