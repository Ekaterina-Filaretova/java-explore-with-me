package ru.practicum.hits;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;
import ru.practicum.hits.dto.EndpointHitDto;
import ru.practicum.hits.dto.ViewStatsDto;
import ru.practicum.hits.specifications.ViewStatsSpecifications;
import ru.practicum.hits.specifications.model.ViewStatsRequest;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class EndpointHitServiceImpl implements EndpointHitService {

    private final EndpointHitMapper hitMapper;
    private final EndpointHitRepository hitRepository;

    @Override
    public void addHit(EndpointHitDto endpointHitDto) {
        EndpointHit hit = hitMapper.convertToEntity(endpointHitDto);
        hitRepository.save(hit);
        log.info("Сохранено обращение к эндпоинту {}", hit);
    }

    @Override
    public List<ViewStatsDto> getStats(ViewStatsRequest request) {
        List<EndpointHit> hits = hitRepository.findAll(new ViewStatsSpecifications(request));
        if (request.isUnique()) {
            hits = StreamEx.of(hits)
                    .distinct(EndpointHit::getIp)
                    .toList();
        }
        List<ViewStatsDto> viewsDtos = hitMapper.convertToDto(hits);
        for (ViewStatsDto views : viewsDtos) {
            views.setHits(hitRepository.countByUri(views.getUri()));
        }
        log.info("Получена статистика {}", viewsDtos);
        return viewsDtos;
    }
}
