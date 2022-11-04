package ru.practicum.compilations.admin;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilations.dto.CompilationDto;
import ru.practicum.compilations.dto.NewCompilationDto;

@RestController
@RequestMapping(path = "/admin/compilations")
@AllArgsConstructor
public class AdminCompilationController {

    private final AdminCompilationService adminService;

    @PostMapping
    public CompilationDto addCompilation(@RequestBody NewCompilationDto compilationDto) {
        return adminService.addCompilation(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        adminService.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteCompilationEvent(@PathVariable Long compId,
                                       @PathVariable Long eventId) {
        adminService.deleteCompilationEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addCompilationEvent(@PathVariable Long compId,
                                    @PathVariable Long eventId) {
        adminService.addCompilationEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable Long compId) {
        adminService.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable Long compId) {
        adminService.pinCompilation(compId);
    }
}