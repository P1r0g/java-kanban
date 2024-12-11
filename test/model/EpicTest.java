package model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

class EpicTest {

    private Epic epic;

    @BeforeEach
    void setup() {
        epic = new Epic("Test Epic", Status.DONE, "Test description", Duration.ZERO, LocalDateTime.now());
    }

    @Test
    void shouldBeNewWhenAllSubtasksAreNew() {
        SubTask sub1 = new SubTask(2, "Subtask 1", Status.NEW, "Description", Duration.ofMinutes(30), LocalDateTime.now(), epic);
        SubTask sub2 = new SubTask(3, "Subtask 2", Status.NEW, "Description", Duration.ofMinutes(45),
                LocalDateTime.now().plusMinutes(30), epic);
        epic.addTask(sub1);
        epic.addTask(sub2);

        epic.updateStatus();

        Assertions.assertEquals(Status.NEW, epic.getStatus(), "Статус эпика должен быть NEW, если все подзадачи NEW");
    }

    @Test
    void shouldBeDoneWhenAllSubtasksAreDone() {
        SubTask sub1 = new SubTask(2, "Subtask 1", Status.DONE, "Description", Duration.ofMinutes(30), LocalDateTime.now(), epic);
        SubTask sub2 = new SubTask(3, "Subtask 2", Status.DONE, "Description", Duration.ofMinutes(45),
                LocalDateTime.now().plusMinutes(30), epic);
        epic.addTask(sub1);
        epic.addTask(sub2);

        epic.updateStatus();

        Assertions.assertEquals(Status.DONE, epic.getStatus(), "Статус эпика должен быть DONE, если все подзадачи DONE");
    }

    @Test
    void shouldBeInProgressWhenSubtasksHaveMixedStatuses() {
        SubTask sub1 = new SubTask(2, "Subtask 1", Status.NEW, "Description", Duration.ofMinutes(30), LocalDateTime.now(), epic);
        SubTask sub2 = new SubTask(3, "Subtask 2", Status.DONE, "Description", Duration.ofMinutes(45), LocalDateTime.now().plusMinutes(30),
                epic);
        epic.addTask(sub1);
        epic.addTask(sub2);

        epic.updateStatus();

        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS, если подзадачи NEW и DONE");
    }

    @Test
    void shouldBeInProgressWhenAllSubtasksAreInProgress() {
        SubTask sub1 = new SubTask(2, "Subtask 1", Status.IN_PROGRESS, "Description", Duration.ofMinutes(30), LocalDateTime.now(), epic);
        SubTask sub2 = new SubTask(3, "Subtask 2", Status.IN_PROGRESS, "Description", Duration.ofMinutes(45), LocalDateTime.now().plusMinutes(30), epic);
        epic.addTask(sub1);
        epic.addTask(sub2);

        epic.updateStatus();

        Assertions.assertEquals(Status.IN_PROGRESS, epic.getStatus(), "Статус эпика должен быть IN_PROGRESS, если все подзадачи IN_PROGRESS");
    }
}