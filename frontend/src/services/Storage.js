export function getDataFromStorage(storage) {
  return {
    name: JSON.parse(storage.getItem('name')) || '',
    disciplines: JSON.parse(storage.getItem('disciplines')) || [],
    departments: JSON.parse(storage.getItem('departments')) || [],
    keywords: JSON.parse(storage.getItem('keywords')) || [],
    courseCode: JSON.parse(storage.getItem('courseCode')) || '',
  };
}

export function getComputedDataFromStorage(storage) {
  return {
    name: JSON.parse(storage.getItem('name')) || '',
    keywords: JSON.parse(storage.getItem('keywords')) || [],
    courseCode: JSON.parse(storage.getItem('courseCode')) || '',
    disciplines:
      getComputedObjects(JSON.parse(storage.getItem('disciplines'))) || [],
    departments:
      getComputedObjects(JSON.parse(storage.getItem('departments'))) || [],
  };
}

export function saveDataInStorage(storage, payload) {
  const { userData, backendData } = payload;
  const { isValid, errors } = validateFields(userData, backendData);

  if (!isValid) return errors;

  const { name, courseCode, disciplines, departments, keywords } = userData;
  const { allDepartments, allDisciplines } = backendData;

  storage.clear();

  const selectedDepartments = getFullObject(allDepartments, departments);
  const selectedDisciplines = getFullObject(allDisciplines, disciplines);
  const info = {
    name,
    courseCode,
    disciplines: selectedDisciplines,
    departments: selectedDepartments,
    keywords,
  };

  Object.keys(info).forEach((field) => {
    storage.setItem(field, JSON.stringify(info[field]));
  });

  return [];
}

function getComputedObjects(objects) {
  return objects?.map((obj) => `${obj.code} - ${obj.name}`);
}

function getFullObject(allObjects, selecteds) {
  return selecteds.map((selected) =>
    allObjects.find(({ code }) => {
      const [inputCode, _] = selected.split('-');
      return inputCode.trim() == code;
    })
  );
}

function validateFields(userData, backendData) {
  const { name, courseCode, departments, keywords } = userData;
  const { allCoursesCode } = backendData;
  const errors = [];

  if (!name.trim()) errors.push('É necessário informar seu nome');

  if (!allCoursesCode.find((code) => code === courseCode)) {
    errors.push('Código do curso não existe');
  }

  if (departments.length === 0)
    errors.push('É necessário informar ao menos um departamento de interesse');
  if (keywords.length === 0)
    errors.push('É necessário informar ao menos um tópico de interesse');

  return {
    isValid: errors.length === 0,
    errors,
  };
}
