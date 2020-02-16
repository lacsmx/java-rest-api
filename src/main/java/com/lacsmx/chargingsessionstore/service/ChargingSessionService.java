package com.lacsmx.chargingsessionstore.service;

import com.lacsmx.chargingsessionstore.enums.StatusEnum;
import com.lacsmx.chargingsessionstore.models.ChargingSession;
import com.lacsmx.chargingsessionstore.models.Summary;
import com.lacsmx.chargingsessionstore.storage.DataRepositoryInterface;
import com.lacsmx.chargingsessionstore.storage.InMemoryStorage;
import com.lacsmx.chargingsessionstore.utils.Utils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Service to connect with a DataRepository and operate over it.
 * Creation, update and search operation are allowed.
 * It provides a report to see total of sessions in a time window
 *
 * @author CS Luis Date: Jan 18, 2020
 */

@Service
public class ChargingSessionService implements GeneralServiceInterface<ChargingSession> {
    // Instance for saving all charging sessions in memory
    private static DataRepositoryInterface repository = new InMemoryStorage();

    /**
     * Extract id from charging session object to store it in our inmemory storage
     * @param newRecord
     * @return
     */
    @Override
    public ChargingSession create(ChargingSession newRecord) {
        System.out.println("ChargingSessionService create");
        return (ChargingSession) repository.save(newRecord.getId().toString(), newRecord);
    }

    /**
     * Insert the new information for  particular session
     * If the session exists then it is replaced by the new one
     * otherwise nothing happens
     * @param id
     * @param updatedRecord
     * @return
     */
    @Override
    public ChargingSession update(String id, ChargingSession updatedRecord) {
        System.out.println("ChargingSessionService update");
        //Get stored charging session
        ChargingSession currentChargingSession = (ChargingSession) repository.search(id);
        if (currentChargingSession != null) {
            // Update status and time for the current session
            currentChargingSession.setStoppedAt(LocalDateTime.now());
            currentChargingSession.setStatus(StatusEnum.FINISHED);
            return (ChargingSession) repository.update(id, currentChargingSession);
        }
        return null;
    }

    /**
     * Ask the repository for a particular element in it
     * @param id
     * @return
     */
    @Override
    public ChargingSession getById(String id) {
        System.out.println("ChargingSessionService getById");
        return (ChargingSession) repository.search(id);
    }

    /**
     * Get all the registered sessions stored in our in memory repository
     * @return
     */
    @Override
    public Collection<ChargingSession> getAll() {
        System.out.println("ChargingSessionService getAll");
        return repository.getAll();
    }

    /**
     * Report total of sessions within a time window
     * timewindow is set to 1
     * It considers start and stop date separately
     * @return
     */
    public Summary summary(){
        Collection<ChargingSession> sessions = repository.getAll();
        // TODO read this configuration value from properties file
        // Time window value for doing the summary
        long timeWindowInMinutes = 1;

        // Getting count for started
        long startedCount = countByTimeWindow(sessions.stream()
                .map(ChargingSession::getStartedAt), timeWindowInMinutes);
        // Getting count for stopped
        long stoppedCount = countByTimeWindow(sessions.stream()
                .map(ChargingSession::getStoppedAt), timeWindowInMinutes);

        return new Summary(startedCount+ stoppedCount, startedCount, stoppedCount);
    }

    /**
     * Return the number of times within a time window, from now time to now - time_window
     * filtering null values
     *
     * @param sessions
     * @param timeWindowInMinutes
     * @return
     */
    private long countByTimeWindow(Stream<LocalDateTime> sessions, long timeWindowInMinutes){
        // Gettting our start time
        LocalDateTime currentTime = LocalDateTime.now();
        // Filtering the sessions within time window period
        return sessions.filter(Objects::nonNull)
                .filter(timeValue -> Utils.isInTimeWindow(timeValue, currentTime.minusMinutes(timeWindowInMinutes),currentTime ))
                .count();
    }
}
