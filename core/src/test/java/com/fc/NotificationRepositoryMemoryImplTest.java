package com.fc;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotificationRepositoryMemoryImplTest {
  private NotificationRepositoryMemoryImpl sut = new NotificationRepositoryMemoryImpl();

  private final Instant now = Instant.now();
  private final Instant deletedAt = Instant.now().plus(90, DAYS);

  @Test
  void test_save() {
    // 알림 객체 생성
    Notification test_noti = new Notification("1", 2L, NotificationType.LIKE, now, deletedAt);

    // 저장
    sut.save(test_noti);

    // 조회했을 때 객체가 있는지 확인
    Optional<Notification> found = sut.findById("1");
    assertTrue(found.isPresent());
  }

  @Test
  void test_find_by_id() {
    // 알림 객체 생성
    Notification test_noti = new Notification("2", 2L, NotificationType.LIKE, now, deletedAt);

    // 저장
    sut.save(test_noti);

    // 조회했을 때 객체가 있는지 확인
    Optional<Notification> optionalFound = sut.findById("2");
    Notification notification = optionalFound.orElseThrow();

    assertEquals("2", notification.getId());
    assertEquals(2L, notification.getUserId());
    assertEquals(now, notification.getCreatedAt());
    assertEquals(deletedAt, notification.getDeletedAt());
  }

  @Test
  void test_delete_by_id() {
    // 알림 객체 생성
    Notification test_noti = new Notification("3", 2L, NotificationType.LIKE, now, deletedAt);

    // 저장
    sut.save(test_noti);

    sut.deleteById("3");
    Optional<Notification> optionalFound = sut.findById("3");
    assertTrue(optionalFound.isEmpty());
  }
}