export async function fetchDataFromBackend(path, body) {
  const url = process.env.BACKEND_URL || 'http://localhost:8080';

  const headers = new Headers();
  headers.append('Content-Type', 'application/json');

  const response = await fetch(url + path, {
    headers,
    method: 'POST',
    body: JSON.stringify(body),
  });

  if (response.status === 200) {
    return await response.json();
  }
  return null;
}

export async function getAllFormDataFromBackend(path) {
  const url = process.env.BACKEND_URL || 'http://localhost:8080';

  const response = await fetch(url + path);

  if (response.status === 200) {
    return await response.json();
  }
  return null;
}
