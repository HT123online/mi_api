
import json
import os
import requests

# URL base de la API local
BASE_URL = os.getenv("API_BASE_URL", "http://127.0.0.1:5000")

# Definición de la batería de pruebas de seguridad (Casos de prueba basados en las reglas)
CASOS_DE_PRUEBA = [
    {
        "id_prueba": "TEST-SEC-01",
        "vector_ataque": "Generar QR con monto negativo (-50.00)",
        "metodo": "POST",
        "endpoint": "/generar/-50.00/hamburguesa",
        "headers": {},
        "payload": {}
    },
    {
        "id_prueba": "TEST-SEC-02",
        "vector_ataque": "Generar QR con monto cero (0.00)",
        "metodo": "POST",
        "endpoint": "/generar/0.00/hamburguesa",
        "headers": {},
        "payload": {}
    },
    {
        "id_prueba": "TEST-SEC-03",
        "vector_ataque": "Generar QR con monto excesivo mayor a 5000 (999999.00)",
        "metodo": "POST",
        "endpoint": "/generar/999999.00/hamburguesa",
        "headers": {},
        "payload": {}
    },
    {
        "id_prueba": "TEST-SEC-04",
        "vector_ataque": "Generar QR sin cabeceras de autenticación de comercio",
        "metodo": "POST",
        "endpoint": "/generar/25.50/combo_familiar",
        "headers": {}, # Sin Authorization / API-Key
        "payload": {}
    },
    {
        "id_prueba": "TEST-SEC-05",
        "vector_ataque": "Generar imagen QR con texto plano sin firma criptográfica",
        "metodo": "GET",
        "endpoint": "/qr/monto=25.50&cuenta=123/temporal",
        "headers": {},
        "payload": {}
    }
]

def ejecutar_pruebas():
    print(f"[*] Iniciando ejecución de pruebas de seguridad contra: {BASE_URL}")
    evidencias = []

    for caso in CASOS_DE_PRUEBA:
        url = f"{BASE_URL}{caso['endpoint']}"
        print(f"\n[-] Ejecutando {caso['id_prueba']}: {caso['vector_ataque']}")
        
        peticion_info = {
            "metodo": caso["metodo"],
            "url": url,
            "headers": caso["headers"],
            "payload": caso["payload"]
        }
        
        try:
            if caso["metodo"] == "POST":
                response = requests.post(url, json=caso["payload"], headers=caso["headers"], timeout=5)
            elif caso["metodo"] == "GET":
                response = requests.get(url, headers=caso["headers"], timeout=5)
            else:
                response = requests.request(caso["metodo"], url, headers=caso["headers"], timeout=5)

            # Intentar parsear respuesta como JSON o guardar texto
            try:
                cuerpo_respuesta = response.json()
            except Exception:
                cuerpo_respuesta = response.text[:200]  # Si es imagen/HTML, truncar resumen

            respuesta_info = {
                "status_code": response.status_code,
                "headers": dict(response.headers),
                "body": cuerpo_respuesta
            }
            print(f"    -> Código HTTP recibido: {response.status_code}")

        except requests.exceptions.ConnectionError:
            print(f"    [!] Error: No se pudo conectar a la API en {url}. ¿Está encendido el servidor Flask?")
            respuesta_info = {
                "status_code": 0,
                "error": "ConnectionRefusedError: Servidor no disponible"
            }
        except Exception as e:
            respuesta_info = {
                "status_code": 0,
                "error": str(e)
            }

        # Estructurar evidencia en el formato exacto que espera el Prompt Maestro de Bob AI
        evidencia = {
            "id_prueba": caso["id_prueba"],
            "vector_ataque": caso["vector_ataque"],
            "peticion_enviada": peticion_info,
            "respuesta_obtenida": respuesta_info
        }
        evidencias.append(evidencia)

    # Guardar evidencias en disco para alimentar a Bob AI
    archivo_evidencias = "evidencias.json"
    with open(archivo_evidencias, "w", encoding="utf-8") as f:
        json.dump(evidencias, f, indent=2, ensure_ascii=False)

    print(f"\n[+] Pruebas completadas. Evidencias guardadas en '{archivo_evidencias}'.")
    return evidencias

if __name__ == "__main__":
    ejecutar_pruebas()