# Highway Vignette Application

This is the Android client application for retrieving and ordering highway vignettes and vehicle info.

## API Backend Setup

The application communicates with a Dockerized backend API. Below are the details and installation instructions for running the API locally.

### API Specification Overview
- **OpenAPI Version**: 3.0.0
- **Title**: Highway Vignette API
- **Description**: API for retrieving and ordering highway vignettes and vehicle info.
- **Version**: 1.0.0
- **Default Local URL**: `http://0.0.0.0:8080`

### Installation & Running with Docker

1. Ensure you have [Docker](https://www.docker.com/) installed and running on your machine.
2. Navigate to the directory containing the API's `docker-compose.yml` or Docker setup.
3. Start the containerized API using the following command:
   ```bash
   docker-compose up -d
   ```
4. The API should now be running and accessible at `http://localhost:8080` (or `http://0.0.0.0:8080`).

---

## ⚠️ Important Note regarding Device Connectivity

> [!IMPORTANT]
> Right now, the application's base URL configuration is optimized for **Android Emulators** (pointing to the local host interface or loopback address). 
>
> **This configuration will NOT work out of the box for real physical devices**, as they cannot access `localhost` or `10.0.2.2` of your development machine directly over the network. 
> 
> To test the application on a **real device**:
> 1. Ensure both your development machine and your Android device are connected to the same Wi-Fi network.
> 2. Determine your development machine's local IP address (e.g., `192.168.x.x`).
> 3. Update the API base URL configuration in the Android project's network/di module to use your machine's local IP address instead of `10.0.2.2` or `0.0.0.0`.
