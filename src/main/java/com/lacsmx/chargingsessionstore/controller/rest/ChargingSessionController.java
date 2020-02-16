package com.lacsmx.chargingsessionstore.controller.rest;

import com.lacsmx.chargingsessionstore.models.ChargingSession;
import com.lacsmx.chargingsessionstore.models.Summary;
import com.lacsmx.chargingsessionstore.service.ChargingSessionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

/**
 * Rest endpoints to provide access and manipulation over different charging sessions
 *
 * @author CS Luis Date: Jan 18, 2020
 */

@RestController
@RequestMapping(value="/api/v1/chargingSessions/")
@Api(value="Charging Sessions Store")
public class ChargingSessionController {

    // Unique entry for our charging session data
    @Autowired
    ChargingSessionService chargingSessionService;

    @ApiOperation(value = "Create a new record for a charging session", response = ChargingSession.class)
    @PostMapping("/")
    public ResponseEntity<ChargingSession> create(@Valid @RequestBody ChargingSession paramChargingSession) {
        ChargingSession chargingSession = chargingSessionService.create(
                new ChargingSession(paramChargingSession.getStationId()));
        if( chargingSession == null){
            return new ResponseEntity<ChargingSession>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<ChargingSession>(chargingSession, HttpStatus.OK);
    }

    @ApiOperation(value = "Update Status of a particular charging session", response = ChargingSession.class)
    @PutMapping("/{id}")
    public ResponseEntity<ChargingSession> update(@PathVariable("id") String sessionId) {
        ChargingSession chargingSession = chargingSessionService.update(sessionId,null);
        if( chargingSession == null){
            return new ResponseEntity<ChargingSession>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ChargingSession>(chargingSession, HttpStatus.OK);
    }


    @ApiOperation(value = "Return list of available charging sessions", response = Collection.class)
    @GetMapping("/")
    public Collection<ChargingSession> getAll() {
         return chargingSessionService.getAll();
    }

    @ApiOperation(value = "Return an specific charging session by id", response = Collection.class)
    @GetMapping("/{id}")
    public ResponseEntity<ChargingSession> get(@PathVariable("id") String sessionId) {
        ChargingSession chargingSession = chargingSessionService.getById(sessionId);
        if( chargingSession == null){
            return new ResponseEntity<ChargingSession>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ChargingSession>(chargingSession, HttpStatus.OK);
    }

    @ApiOperation(value = "Return a summary considering the last minute charging sessions", response = Summary.class)
    @GetMapping("/summary")
    public ResponseEntity<Summary> summary() {
        Summary summary = chargingSessionService.summary();
        if( summary == null){
            return new ResponseEntity<Summary>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Summary>(summary, HttpStatus.OK);
    }
}
