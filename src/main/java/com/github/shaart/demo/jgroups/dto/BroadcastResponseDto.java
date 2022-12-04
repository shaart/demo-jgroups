package com.github.shaart.demo.jgroups.dto;

import com.github.shaart.demo.jgroups.model.BroadcastResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BroadcastResponseDto {

    private Map<BroadcastResult, List<String>> result;
}
