package mk.ukim.finki.timskiproekt.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.ukim.finki.timskiproekt.model.Course;
import mk.ukim.finki.timskiproekt.model.Room;
import mk.ukim.finki.timskiproekt.model.enums.Semester;
import mk.ukim.finki.timskiproekt.repository.CourseRepository;
import mk.ukim.finki.timskiproekt.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public Course getCourseByName(String name) {
        log.info("Getting course by name: {}", name);
        return this.courseRepository.findByName(name);
    }

    @Override
    public Course getCourseByCode(String code) {
        log.info("Getting course by code: {}", code);
        return this.courseRepository.findByCode(code);
    }

    @Override
    public List<Course> getAllCourses() {
        log.info("Getting all courses");
        return this.courseRepository.findAll();
    }

    @Override
    public List<Course> getAllCoursesBySemester(Semester semester) {
        log.info("Getting course by semester: {}", semester.toString());
        return this.courseRepository.findAllBySemester(semester);
    }

    @Override
    public List<Room> getAllRoomsByCourse(String courseCode) {
        log.info("Getting all rooms by course: {}", courseCode);
        return this.courseRepository.findByCode(courseCode).getRooms();
    }

    @Override
    public Course createCourse(String courseName, String courseCode, Semester semester) {
        log.info("Creating course with name: {}, and code: {}", courseName, courseCode);
        return this.courseRepository.save(new Course(null, courseName, courseCode, semester, new ArrayList<>()));
    }

    @Override
    public void deleteCourseByName(String name) {
        log.info("Deleting course with name: {}", name);
        this.courseRepository.deleteByName(name);
    }

    @Override
    public Room addRoomToCourse(Room room, String courseCode) {
        log.info("Adding room {}: , to course with code: {}", room.getName(), courseCode);
        Course course = this.courseRepository.findByCode(courseCode);
        course.getRooms().add(room);
        this.courseRepository.save(course);
        return room;
    }

    @Override
    public void deleteRoomFromCourse(Room room, String courseCode) {
        log.info("Deleting room {}: , from course with code: {}", room.getName(), courseCode);
        Course course = this.courseRepository.findByCode(courseCode);
        course.getRooms().remove(room);
        this.courseRepository.save(course);
    }
}