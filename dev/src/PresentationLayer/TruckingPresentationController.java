package PresentationLayer;

import ServiceLayer.TruckingService;

public class TruckingPresentationController {
    private final TruckingService truckingService;
    private static TruckingPresentationController instance = null;

    private TruckingPresentationController() {
        this.truckingService = TruckingService.getInstance();
    }

    public static TruckingPresentationController getInstance() {
        if (instance == null)
            instance = new TruckingPresentationController();
        return instance;
    }
}
